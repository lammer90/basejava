package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.*;
import com.urise.webapp.srorage.Storage;
import com.urise.webapp.util.JSONConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
            for (SectionType sectionType : SectionType.values()) {
                addSectionn(sectionType, resume, request.getParameter(sectionType.name()));
            }
            storage.update(resume);
        }
        else{
            Resume resume = new Resume(request.getParameter("fullname"));
            for (Contacts contact : Contacts.values()) {
                resume.addContact(contact, request.getParameter(contact.name()));
            }
            for (SectionType sectionType : SectionType.values()) {
                addSectionn(sectionType, resume, request.getParameter(sectionType.name()));
            }
            storage.save(resume);
        }
        response.sendRedirect("resume");
    }

    private void addSectionn(SectionType sectionType, Resume resume, String parameter) {
        if (sectionType.getaClass() == StringSection.class){
            resume.addSectionn(sectionType, new StringSection(parameter));
        }
        else if (sectionType.getaClass() == ArraySection.class){
            resume.addSectionn(sectionType, new ArraySection(parameter.split(System.lineSeparator())));
        }
        else {
            String[] arr = parameter.split(System.lineSeparator());
            Conteiner[] con = new Conteiner[arr.length];
            for (int i = 0; i < arr.length; i++){
                try {
                    con[i] = JSONConverter.read(arr[i], Conteiner.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resume.addSectionn(sectionType, new ConteinerSection(con));
        }
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
