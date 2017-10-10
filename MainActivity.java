<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"package com.example.giuseppebotta.ana;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ArrowKeyMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddStudentDialog.AddStudentDialogListener,
        UpdateStudentInfo.UpdateStudentDialogListener, DeleteStudentDialog.DeleteStudentDialogListener {

    Button btnAddStudent, btnUpdateRec, btnShowDettagli, btnDeleteRec;
    TextView tvStdInfo,textView;
    private String TAG = "";
    SQLiteDatabase dtb;
    int btnBackPressCounter = 0;
    ImageView imgView1,imgView2;
    DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);

        textView = (TextView)findViewById(R.id.textView);
        tvStdInfo = (TextView) findViewById(R.id.tvStdList);
        btnAddStudent = (Button)findViewById(R.id.btnAddStudent);
        imgView1 = (ImageView)findViewById(R.id.imgView1);
        imgView2 = (ImageView)findViewById(R.id.imgView2);

        btnShowDettagli = (Button)findViewById(R.id.btnShowDettagli);
        btnUpdateRec = (Button)findViewById(R.id.btnUpdateRec);
        btnDeleteRec = (Button)findViewById(R.id.btnDeleteRec);

        // deleghiamo i bottoni
        imgView1.setOnClickListener(this);
        imgView2.setOnClickListener(this);
        btnAddStudent.setOnClickListener(this);
        btnShowDettagli.setOnClickListener(this);
        btnUpdateRec.setOnClickListener(this);
        btnDeleteRec.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddStudent:
                AddStudentDialog dialog = new AddStudentDialog();
                dialog.show(getFragmentManager(), TAG);
                break;

            case R.id.btnShowDettagli:
                //View Block Number List in the Text View Widget

                tvStdInfo.setMovementMethod(ArrowKeyMovementMethod.getInstance());

                tvStdInfo.setText("");	//	clear text area at each button press
                tvStdInfo.setTextColor(Color.BLUE);
                tvStdInfo.setPadding(5, 2, 5, 2);
                view.setPadding(0,5,0,0);

                List<Student> studentsList = db.getAllStudentList();	//	Recuperare l'elenco dei moduli BlockedNumbers del DB - 'getAllBlockedNumbers'

              for (Student std : studentsList) {

                    String stdDetail = "Id:" + std.get_id() + " \tTessera N°:" + std.get_enroll_no() +
                            "\n\tNome:" + std.get_name() + "\n\tTelefono N°:" + std.get_phone_number();
                    tvStdInfo.append("\n" + stdDetail+"\n"+"----------------------------------------");
                }
                break;

            case R.id.btnUpdateRec:
                studentsList = db.getAllStudentList();


                UpdateStudentInfo updateDialog = new UpdateStudentInfo();
                updateDialog.show(getFragmentManager(),TAG);
                break;

            case R.id.btnDeleteRec:
                DeleteStudentDialog deleteDialog = new DeleteStudentDialog();
                deleteDialog.show(getFragmentManager(),TAG);

                break;

            case R.id.imgView1:
               Intent it = new Intent(this,SplashScreen.class);
                startActivity(it);
                break;
            case R.id.imgView2:
                 it = new Intent(this,SplashScreen.class);
                startActivity(it);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveButtonClick(DialogFragment dialog) {

        //	Get Numero Incremento
        EditText entEnrolNo = (EditText) dialog.getDialog().findViewById(R.id.edtEnrollNo);
        String enrollNo = entEnrolNo.getText().toString();
        int int_enrollNo =Integer.parseInt(entEnrolNo.getText().toString());


//		Get Nome
        EditText entName = (EditText) dialog.getDialog().findViewById(R.id.edtName);
        String name = entName.getText().toString();
        //		Get Numero Telefono
        EditText entPhnNo = (EditText) dialog.getDialog().findViewById(R.id.edtPhoneNo);
        String  phnNo = entPhnNo.getText().toString();

        boolean check_enrollNo = checkEnrollNo(enrollNo);

        boolean check_name = checkName(name);

        boolean check_phnNo = checkPhoneNo(phnNo);

        if(check_enrollNo == false || check_name == false || check_phnNo == false){

            StampaToast.stampa(MainActivity.this, "Inserisci i dati nuovamente..");
            //Toast.makeText(getApplicationContext(),"Inserisci i dati nuovamente..",Toast.LENGTH_LONG).show();
        }else{

            db.addNewStudent(new Student(int_enrollNo,name,phnNo));
            stampaRisultato( enrollNo, name, phnNo);
            //Toast.makeText(getApplicationContext(),"Tesserato aggiunto alla lista..",Toast.LENGTH_LONG).show();
            StampaToast.stampa(getApplicationContext(), "Tesserato aggiunto alla lista..");
        }


        //Toast.makeText(getApplicationContext(),"\nNo :" + enrollNo + "\nNome: " + name + "\nTel. No:" + phnNo,Toast.LENGTH_LONG).show();



    }

    //Check Id numero
    public boolean checkIdno(String Id_No){

        if(Id_No.equals("")){
            return false;
        }else{
            return true;
        }
    }
    //Check Numero incremento
    public boolean checkEnrollNo(String enr_No){

        if(enr_No.equals("")  || enr_No.length() != 7){

            return false;
        }else{
            return true;
        }
    }
    //Check Nome Cognome
    public boolean checkName(String stdName){

        if(stdName.equals("")){
            return false;
        }else{
            return true;
        }
    }
    //Check Numero telefono
    public boolean checkPhoneNo(String phn_No){

        if(phn_No.equals("") || phn_No.length() != 10){

            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onUpdateButtonClick(DialogFragment dialog) {
//		Get ID
        EditText entId = (EditText) dialog.getDialog().findViewById(R.id.edt_UpdateId);
        String idNo = entId.getText().toString();
        int int_idNo =Integer.parseInt(entId.getText().toString());
        //		Get NumeroIncremento
        EditText entEnrolNo = (EditText) dialog.getDialog().findViewById(R.id.edt_UpdateEnrollNo);
        String enrollNo = entEnrolNo.getText().toString();
        int int_enrollNo =Integer.parseInt(entEnrolNo.getText().toString());
//		Get Name
        EditText entName = (EditText) dialog.getDialog().findViewById(R.id.edt_UpdateName);
        String name = entName.getText().toString();
        //		Get Numero telefono
        EditText entPhnNo = (EditText) dialog.getDialog().findViewById(R.id.edt_UpdatePhoneNo);
        String  phnNo = entPhnNo.getText().toString();

        boolean check_idNo = checkIdno(idNo);

        boolean check_enrollNo = checkEnrollNo(enrollNo);

        boolean check_name = checkName(name);

        boolean check_phnNo = checkPhoneNo(phnNo);

        if(check_idNo == false  || check_enrollNo == false || check_name == false || check_phnNo == false){

            StampaToast.stampa(MainActivity.this, "Inserisci i dati nuovamente..");
        }else{

            boolean updateCheck = db.updateStudentInfo(int_idNo, int_enrollNo, name, phnNo);

            if(updateCheck == true){
                stampaRisultato( enrollNo, name, phnNo);
                StampaToast.stampa(MainActivity.this, "Tesserato aggiunto alla lista..");

                }
            else{
                StampaToast.stampa(MainActivity.this, "Aggiornamento dei dettagli non riuscito..");

            }
        }
    }

    public void callTemp(View v){

        startActivity(new Intent(MainActivity.this, SplashScreen.class));
    }

    @Override
    public void onDeleteButtonClick(DialogFragment dialog) {
        //		Get ID
        EditText entId = (EditText) dialog.getDialog().findViewById(R.id.edt_deleteID);
        String idNo = entId.getText().toString();
        int int_idNo =Integer.parseInt(entId.getText().toString());

        boolean check_idNo = checkIdno(idNo);

        if(check_idNo == false){
            StampaToast.stampa(MainActivity.this, "Inserire nuovamente ID corretto..!");
            //Toast.makeText(getApplicationContext(),"Inserire nuovamente ID corretto..!",Toast.LENGTH_LONG).show();

        }else{

            boolean deleCheck = db.deleteStudent(int_idNo);

            if(deleCheck == true){
                StampaToast.stampa(MainActivity.this, "Tesserato cancellato con successo");
                //Toast.makeText(getApplicationContext(),"Tesserato cancellato con successo",Toast.LENGTH_LONG).show();
                }
            else{
                StampaToast.stampa(MainActivity.this, "Cancellazione Tesserato fallita..");
                //Toast.makeText(getApplicationContext(),"Cancellazione Tesserato fallita..",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //CODICE PER ESCIRE SOLTANTO SE SI FA' DOPPIO CLICK
        ++btnBackPressCounter;
        if(btnBackPressCounter == 1){
            StampaToast.stampa(MainActivity.this, "Fare clic su Indietro ancora una volta per uscire");
            //Toast.makeText(getBaseContext(), "Fare clic su Indietro ancora una volta per uscire", Toast.LENGTH_SHORT).show();

        }
        else {
			/*super.onBackPressed();
            if (interAd.isLoaded()) {
                interAd.show();*/
            finish();
        }
    }
    public void stampaRisultato(String enrollNo, String name, String phnNo ){
        tvStdInfo.setText("\nNo :" + enrollNo + "\nNome: " + name + "\nTel. No:" + phnNo+"\nAGGIUNTO!");
    }
}

