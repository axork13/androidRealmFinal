package com.example.eservicefa.androidrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eservicefa.androidrealm.model.Student;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText marks;
    private TextView log;
    private Button add;
    private Button view;
    private Button update;
    private Button delete;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        name = (EditText)findViewById(R.id.editText_name);
        marks = (EditText)findViewById(R.id.editText_marks);
        add = (Button)findViewById(R.id.button_add);
        view = (Button)findViewById(R.id.button_view);
        update = (Button)findViewById(R.id.button_update);
        delete = (Button)findViewById(R.id.button_delete);
        log = (TextView)findViewById(R.id.textView_log);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDB(name.getText().toString().trim(), Integer.parseInt(marks.getText().toString()));
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                int i = Integer.parseInt(marks.getText().toString());
                updateDB(n, i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                deleteFromDB(n);
            }
        });


    }

    public void addToDB(final String name, final int marks) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Student student = bgRealm.createObject(Student.class);

                student.setName(name);
                student.setMarks(marks);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Database", "Donnée insérées");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Database", error.getMessage());
            }
        });
    }

    public void updateDB(final String name, final int marks) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Student student = bgRealm.where(Student.class).equalTo("name", name).findFirst();
                student.setMarks(marks);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Database", "Donnée mise à jour");
            }
        });
    }


    public void deleteFromDB(final String name) {
        final RealmResults<Student> students = realm.where(Student.class).equalTo("name", name).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                students.deleteFromRealm(0);
            }
        });
    }

    public void showData() {
        RealmResults<Student> students = realm.where(Student.class).findAll();
        students.load();
        String op = "";

        for (Student student : students) {
            op+=student.toString();
        }

        log.setText(op);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
