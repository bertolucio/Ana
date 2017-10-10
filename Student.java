package com.example.giuseppebotta.ana;

/**
 * Created by giuseppebotta on 03/05/17.
 */


public class Student {

    //Private Variabili
    int _id;
    int _enroll_no;
    String _name;
    String _phone_number;

    //svuoto construttore
    public Student() {
    }

    //Tutti i parametri in Costruttore
    public Student(int _id, String _name, int _enroll_no, String _phone_number) {
        this._id = _id;
        this._name = _name;
        this._enroll_no = _enroll_no;
        this._phone_number = _phone_number;
    }

    //Tre parametri Costruttore
    public Student(int _enroll_no, String _name, String _phone_number) {
        this._enroll_no = _enroll_no;
        this._name = _name;
        this._phone_number = _phone_number;
    }


    //Getters per tutti i campi

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public int get_enroll_no() {
        return _enroll_no;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    //Setters for all fields
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_enroll_no(int _enroll_no) {
        this._enroll_no = _enroll_no;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }
}
