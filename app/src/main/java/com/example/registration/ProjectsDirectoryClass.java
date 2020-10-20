package com.example.registration;

public class ProjectsDirectoryClass {
    private String projectsClassName;
    private String directoryName;
    private int drawableForDir;
    private int drawableForFile;
    private String fileName;

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDrawableForDir() {
        return drawableForDir;
    }

    public void setDrawableForDir(int drawableForDir) {
        this.drawableForDir = drawableForDir;
    }

    public int getDrawableForFile() {
        return drawableForFile;
    }

    public void setDrawableForFile(int drawableForFile) {
        this.drawableForFile = drawableForFile;
    }

    public ProjectsDirectoryClass(String projectsClassName, String directory, String fileName, int drawable_1, int drawable_2) {
        this.projectsClassName = projectsClassName;
        this.directoryName = directory;
        this.drawableForDir = drawable_1;
        this.drawableForFile = drawable_2;
        this.fileName = fileName;
    }


    public String getProjectsClassName() {
        return projectsClassName;
    }

    public void setProjectsClassName(String projectsClassName) {
        this.projectsClassName = projectsClassName;
    }

}
