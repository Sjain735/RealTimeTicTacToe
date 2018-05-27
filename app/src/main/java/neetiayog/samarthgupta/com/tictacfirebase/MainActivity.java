package neetiayog.samarthgupta.com.tictacfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView btHost, btJoin;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btHost = (TextView) findViewById(R.id.bt_host);
        btJoin = (TextView) findViewById(R.id.bt_join);

        btHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Host host = new Host();
                host.setTurn(false);

                Away away = new Away();
                away.setTurn(false);

                Game game = new Game();
                game.setHost(host);
                game.setAway(away);
                game.setLastMove(0);
                game.setStarted(false);
                game.setHostZero(true);

                String hostNumber = "9582184794";
                databaseReference.child(hostNumber).setValue(game);

                Intent intent = new Intent(MainActivity.this, GameAltActivity.class);
                intent.putExtra("isHost", true);
                intent.putExtra("code", hostNumber);
                startActivity(intent);
                finish();

            }
        });

        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, JoinActivity.class));
                finish();

            }
        });


    }
}
