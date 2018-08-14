package com.urise.webapp.web;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.ArraySection;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;
import com.urise.webapp.model.section.StringSection;
import com.urise.webapp.srorage.SqlStorage;
import com.urise.webapp.srorage.Storage;
import org.postgresql.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "1234lammer");
        String str = request.getParameter("uuid");
        if (str != null) {
            try {
                Resume resume = storage.get(str);
                getResume(response, resume);
            } catch (NotExistStorageException e) {
                getAllResume(response, storage.getAllSorted());
            }
        } else {
            getAllResume(response, storage.getAllSorted());
        }
    }

    private void getAllResume(HttpServletResponse response, List<Resume> resumes) throws IOException {
       /* Path path = Paths.get("C:\\git_tutorial\\work\\hello\\basejava\\storage\\OneResume.html");

        String s = new String(Files.readAllBytes(path));


        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.getWriter().write(s);*/
    }

    private void getResume(HttpServletResponse response, Resume resume) throws IOException {
        Path path = Paths.get("C:\\git_tutorial\\work\\hello\\basejava\\storage\\OneResume.html");
        String s = new String(Files.readAllBytes(path));
        s = s.replaceAll("&Title", resume.getFullname());
        s = s.replaceAll("&Contacts", "Контакты:");
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Contacts, String> entry : resume.getContacts().entrySet()) {
            builder.append("<tr>");
            builder.append("<td><center>").append(entry.getKey().getTitle()).append(":").append("</center></td>");
            builder.append("<td><center>").append(entry.getValue()).append("</center></td>");
            builder.append("<tr>");
        }
        s = s.replaceAll("&ContStrings", builder.toString());
        StringBuilder builder2 = new StringBuilder();
        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            if (entry.getValue() instanceof StringSection) {
                builder2.append("<br><br><br><br>");
                builder2.append("<table>");
                builder2.append("<tr>");
                builder2.append("<td align=\"left\"><center>").append(entry.getKey().getTitle()).append(":").append("</center></td>");
                builder2.append("</tr>");
                builder2.append("<tr>");
                builder2.append("<td><center>").append(((StringSection) entry.getValue()).getInformation()).append("</center></td>");
                builder2.append("</tr>");
                builder2.append("</table>");
            } else {
                builder2.append("<br><br><br><br>");
                builder2.append("<table>");
                builder2.append("<tr>");
                builder2.append("<td align=\"left\"><center>").append(entry.getKey().getTitle()).append(":").append("</center></td>");
                builder2.append("</tr>");
                builder2.append("<tr>");
                builder2.append("<td><center>").append(String.join ( System.lineSeparator(), ((ArraySection) entry.getValue()).getInformation())).append("</center></td>");
                builder2.append("</tr>");
                builder2.append("</table>");
            }

        }
        s = s.replaceAll("&Sections", builder2.toString());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(s);
    }
}
