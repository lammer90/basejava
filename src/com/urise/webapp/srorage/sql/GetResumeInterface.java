package com.urise.webapp.srorage.sql;

import com.urise.webapp.model.Resume;

import java.sql.ResultSet;
import java.util.List;

public interface GetResumeInterface {
    List<Resume> getNewResume(ResultSet resultSet);
}
