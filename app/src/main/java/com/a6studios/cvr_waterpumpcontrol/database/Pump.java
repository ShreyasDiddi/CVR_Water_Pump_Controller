package com.a6studios.cvr_waterpumpcontrol.database;

public class Pump {
    public static final String TABLE_NAME = "pumps";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_PHONENUMBER = "phno";

    private int id;
    private String label;
    private String phno;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LABEL+ " TEXT,"
                    + COLUMN_PHONENUMBER+ " TEXT"
                    + ")";

    public Pump() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Pump(int id, String label, String phno) {

        this.id = id;
        this.label = label;
        this.phno = phno;
    }
}
