package com.urise.webapp.srorage.sql;

import com.urise.webapp.model.Resume;

import java.util.List;

public class ExecuteResult {
    private List<Resume> resultSet;
    private int result;

    public ExecuteResult(int result, List<Resume> resultSet) {
        this.resultSet = resultSet;
        this.result = result;
    }

    public List<Resume> getResultSet() {
        return resultSet;
    }

    public int isResult() {
        return result;
    }
}
