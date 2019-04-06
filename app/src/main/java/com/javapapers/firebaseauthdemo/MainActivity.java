package com.javapapers.firebaseauthdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public EditText emailId, passwd, name, pnum, loc, utype;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;

//    demoRef = myRef.child("demo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
         myRef = database.getReference("message");

        name=findViewById(R.id.name);
        pnum=findViewById(R.id.pnumber);
        loc=findViewById(R.id.location);
        utype=findViewById(R.id.usertype);




        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.TVSignIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameS=name.getText().toString();
                final String pnumS=pnum.getText().toString();
                final String locS=loc.getText().toString();
                final String utypeS=utype.getText().toString();
                final String emailID = emailId.getText().toString();
//                myRef.child("users").child(emailID).child("name").setValue("nameS");
                String paswd = passwd.getText().toString();
                String email2=emailID;
                String name2=nameS;
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {

                    Toast.makeText(getApplicationContext(),emailID+" "+nameS,Toast.LENGTH_LONG).show();
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
////                                emailId = findViewById(R.id.ETemail);
////                                final String emailID = emailId.getText().toString();
//
                                Toast.makeText(getApplicationContext(),emailID  + nameS,Toast.LENGTH_LONG).show();
//                                myRef.child("users").child(emailID).child("name").setValue(nameS);
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(I);
            }
        });

    }
    public void savee(View view){
        name=findViewById(R.id.name);
        pnum=findViewById(R.id.pnumber);
        loc=findViewById(R.id.location);
        utype=findViewById(R.id.usertype);
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);


        final String nameS=name.getText().toString();
        final String pnumS=pnum.getText().toString();
        final String locS=loc.getText().toString();
        final String utypeS=utype.getText().toString();
        final String emailID = emailId.getText().toString();

        final String emailID1=emailID.substring(0,emailID.length()-4);

        Toast.makeText(getApplicationContext(),nameS+emailID,Toast.LENGTH_LONG).show();
        try {
            myRef.child("users").child(emailID1).child("name").setValue(nameS);
            myRef.child("users").child(emailID1).child("Phonenumber").setValue(pnumS);
            myRef.child("users").child(emailID1).child("Loacation").setValue(locS);
            myRef.child("users").child(emailID1).child("UserType").setValue(utypeS);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
//    / Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // This method is called once with the initial value and again
//            // whenever data at this location is updated.
//            String value = dataSnapshot.getValue(String.class);
//            Log.d(TAG, "Value is: " + value);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w(TAG, "Failed to read value.", error.toException());
//        }
//    });
}
