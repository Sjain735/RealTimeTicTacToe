package neetiayog.samarthgupta.com.tictacfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    Button btEnter;
    EditText etCode;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btEnter = (Button) findViewById(R.id.bt_enter);
        etCode = (EditText) findViewById(R.id.et_code);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = etCode.getText().toString();

                databaseReference.child("9582184794").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Game game = dataSnapshot.getValue(Game.class);
                        if(game != null){
                            Toast.makeText(JoinActivity.this, game.lastMove + " ", Toast.LENGTH_LONG).show();


                            //Test code
                            game.isStarted = true;
                            game.host.setTurn(true);
                            game.away.setTurn(false);
                            //

                            Intent intent = new Intent(JoinActivity.this, GameAltActivity.class);
                            intent.putExtra("isHost", false);
                            intent.putExtra("code", "9582184794");


                            databaseReference.child("9582184794").setValue(game);

                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(JoinActivity.this, "NO GAME", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });


//        databaseReference.child("9898989898").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Toast.makeText(JoinActivity.this, "Child changed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Toast.makeText(JoinActivity.this, "Any changed", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void doIt() {

    }
}
