package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.ArraySection;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;
import com.urise.webapp.model.section.StringSection;
import com.urise.webapp.srorage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.getInstance().initStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!request.getParameter("uuid").equals("")) {
            Resume resume = new Resume(request.getParameter("fullname"), request.getParameter("uuid"));
            for (Contacts contact : Contacts.values()) {
                resume.addContact(contact, request.getParameter(contact.name()));
            }
            storage.update(resume);
        }
        else{
            Resume resume = new Resume(request.getParameter("fullname"));
            for (Contacts contact : Contacts.values()) {
                resume.addContact(contact, request.getParameter(contact.name()));
            }
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action != null) {
            try {
                switch (action){
                    case "delete": {
                        storage.delete(uuid);
                        response.sendRedirect("resume");
                        break;
                    }
                    case "edit":{
                        request.setAttribute("resume", storage.get(uuid));
                        request.getRequestDispatcher("/WEB-INF/jsp/editResume.jsp").forward(request, response);
                        break;
                    }
                    case "view":{
                        request.setAttribute("resume", storage.get(uuid));
                        request.getRequestDispatcher("/WEB-INF/jsp/viewResume.jsp").forward(request, response);
                        break;
                    }
                    case "add":{
                        request.setAttribute("resume", new Resume());
                        request.getRequestDispatcher("/WEB-INF/jsp/editResume.jsp").forward(request, response);
                        break;
                    }
                }
                /*Resume resume = storage.get(uuid);
                getResume(response, resume);*/
            } catch (NotExistStorageException e) {
               // getAllResume(response, storage.getAllSorted());
            }
        } else {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/allResume.jsp").forward(request, response);
        }
    }
}
