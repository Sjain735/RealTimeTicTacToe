package neetiayog.samarthgupta.com.tictacfirebase;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameAltActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String code;
    TextView tvLoading, tvTurn;
    Game game;
    boolean isHost;

    //Variables for gameplay
    ImageView image_1, image_2, image_3, image_4, image_5, image_6, image_7, image_8, image_9;
    int p_icon = 1; //Represents the turn of player 1 or 2
    int p1_icon = 0; //to check the winning condition
    int rc[][] = new int[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_alt);

        tvLoading = (TextView) findViewById(R.id.tv_loading);
        tvTurn = (TextView) findViewById(R.id.tv_turn);
        //tvTurn.setVisibility(View.INVISIBLE);

        isHost = getIntent().getBooleanExtra("isHost", false);

        code = getIntent().getStringExtra("code");
        code = "9582184794";

        databaseReference.child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Game tempGame = game;
                game = dataSnapshot.getValue(Game.class);
    
                Log.i("CODE","5");
                if (game != null && game.isStarted) {

                    tvLoading.setVisibility(View.GONE);

                    if (isHost) {
                        enableClicks();
                    }

                    boolean hostTurn = game.host.turn;
                    boolean awayTurn = game.away.turn;

                    if (isHost && hostTurn) {
                        Log.i("CODE", "1");
                        //tvTurn.setVisibility(View.VISIBLE);
                        tvTurn.setText("Your Turn: ");
                        //btUpdate.setClickable(true);

                        if(tempGame.lastMove != game.lastMove){
                            makeMove(game.lastMove);
                        }
                        enableClicks();

                    } else if(isHost && awayTurn) {
                        Log.i("CODE", "2");
                        //tvTurn.setVisibility(View.INVISIBLE);
                        tvTurn.setText("Opponent's Turn: ");
                        //btUpdate.setClickable(false);
                        disableClicks();

                    } else if (!isHost && awayTurn) {
                        Log.i("CODE", "3");
                        //tvTurn.setVisibility(View.VISIBLE);
                        tvTurn.setText("Your Turn: ");
                        //btUpdate.setClickable(true);

                        if(tempGame.lastMove != game.lastMove){
                            makeMove(game.lastMove);
                        }
                        enableClicks();

                    } else if(!isHost && hostTurn) {
                        Log.i("CODE", "4");
                        //tvTurn.setVisibility(View.INVISIBLE);
                        tvTurn.setText("Opponent's Turn: ");
                        //btUpdate.setClickable(false);
                        disableClicks();
                    }

                } else {
                    Toast.makeText(GameAltActivity.this, "Game is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        init();
        disableClicks();
        rc_init();

//        if (isHost) {
//            chooseSymbol();
//        }

    }//onCreate()


    private void makeMove(int move){

        int Id = 55;

        switch (move){
            case 1: Id = R.id.game_sign_1; break;
            case 2: Id = R.id.game_sign_2; break;
            case 3: Id = R.id.game_sign_3; break;
            case 4: Id = R.id.game_sign_4; break;
            case 5: Id = R.id.game_sign_5; break;
            case 6: Id = R.id.game_sign_6; break;
            case 7: Id = R.id.game_sign_7; break;
            case 8: Id = R.id.game_sign_8; break;
            case 9: Id = R.id.game_sign_9; break;
            default:Id = 999;
        }

        if (p_icon == 1) {
            p_icon = 2;

            switchCircle(Id);

        } else if (p_icon == 2) {
            p_icon = 1;

            switchCross(Id);

        }//else if

    }

    private void init(){

        image_1 = (ImageView) findViewById(R.id.game_sign_1);
        image_2 = (ImageView) findViewById(R.id.game_sign_2);
        image_3 = (ImageView) findViewById(R.id.game_sign_3);
        image_4 = (ImageView) findViewById(R.id.game_sign_4);
        image_5 = (ImageView) findViewById(R.id.game_sign_5);
        image_6 = (ImageView) findViewById(R.id.game_sign_6);
        image_7 = (ImageView) findViewById(R.id.game_sign_7);
        image_8 = (ImageView) findViewById(R.id.game_sign_8);
        image_9 = (ImageView) findViewById(R.id.game_sign_9);

        image_1.setOnClickListener(this);
        image_2.setOnClickListener(this);
        image_3.setOnClickListener(this);
        image_4.setOnClickListener(this);
        image_5.setOnClickListener(this);
        image_6.setOnClickListener(this);
        image_7.setOnClickListener(this);
        image_8.setOnClickListener(this);
        image_9.setOnClickListener(this);

    }

    private void rc_init(){
        int i,j;

        for (i=0; i<3;i++){
            for (j=0; j<3; j++) {
                rc[i][j] = 5;
            }
        }

    }

    private void chooseSymbol() {

        //Alert Dialogue Box - Starts
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View prompt_view = layoutInflater.inflate(R.layout.layout_alert_dialog,null);

        final AlertDialog ald = new AlertDialog.Builder(this).create();

        Button btnAdd1 = (Button) prompt_view.findViewById(R.id.ad_button_1);
        Button btnAdd2 = (Button) prompt_view.findViewById(R.id.ad_button_2);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_icon = 1;
                p1_icon = 0;
                game.isHostZero = true;
                ald.cancel();
            }
        });
        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_icon = 2;
                p1_icon = 1;
                game.isHostZero = false;
                ald.cancel();
            }
        });

        ald.setView(prompt_view);
        ald.show();
        //Alert Dialogue Box - Ends

        //TODO: Write Game to database.
        //databaseReference.child(code).setValue(game);
    }

    @Override
    public void onClick(View view) {

//        if (view == btUpdate) {
//            if (game != null) {
//                if (isHost) {
//
//                    Log.i("CODE","6");
//                    game.lastMove = 100;
//                    game.host.setTurn(false);
//                    game.away.setTurn(true);
//                    databaseReference.child(code).setValue(game);
//                    tvTurn.setVisibility(View.INVISIBLE);
//
//                } else {
//
//                    Log.i("CODE","7");
//                    game.lastMove = 9;
//                    game.host.setTurn(true);
//                    game.away.setTurn(false);
//                    databaseReference.child(code).setValue(game);
//                    tvTurn.setVisibility(View.INVISIBLE);
//                }
//            }
//        }

            if (p_icon == 1) {
                p_icon = 2;

                switchCircle(view.getId());

            } else if (p_icon == 2) {
                p_icon = 1;

                switchCross(view.getId());

            }//else if

            if (game != null) {
                if (isHost) {

                    Log.i("CODE","6");
                    //game.lastMove = 100;
                    game.host.setTurn(false);
                    game.away.setTurn(true);
                    databaseReference.child(code).setValue(game);
                    //tvTurn.setVisibility(View.INVISIBLE);
                    tvTurn.setText("Opponent's Turn: ");

                } else {

                    Log.i("CODE","7");
                    //game.lastMove = 9;
                    game.host.setTurn(true);
                    game.away.setTurn(false);
                    databaseReference.child(code).setValue(game);
                    //tvTurn.setVisibility(View.INVISIBLE);
                    tvTurn.setText("Opponent's Turn: ");
                }
            }

    }//onClick()

    private void switchCircle(int Id){

        switch (Id){
            case R.id.game_sign_1 : image_1.setImageResource(R.drawable.icon_circle);
                image_1.setClickable(false);
                rc[0][0] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 1;
                break;

            case R.id.game_sign_2 : image_2.setImageResource(R.drawable.icon_circle);
                image_2.setClickable(false);
                rc[0][1] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 2;
                break;

            case R.id.game_sign_3 : image_3.setImageResource(R.drawable.icon_circle);
                image_3.setClickable(false);
                rc[0][2] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 3;
                break;

            case R.id.game_sign_4 : image_4.setImageResource(R.drawable.icon_circle);
                image_4.setClickable(false);
                rc[1][0] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 4;
                break;

            case R.id.game_sign_5 : image_5.setImageResource(R.drawable.icon_circle);
                image_5.setClickable(false);
                rc[1][1] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 5;
                break;

            case R.id.game_sign_6 : image_6.setImageResource(R.drawable.icon_circle);
                image_6.setClickable(false);
                rc[1][2] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 6;
                break;

            case R.id.game_sign_7 : image_7.setImageResource(R.drawable.icon_circle);
                image_7.setClickable(false);
                rc[2][0] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 7;
                break;

            case R.id.game_sign_8 : image_8.setImageResource(R.drawable.icon_circle);
                image_8.setClickable(false);
                rc[2][1] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 8;
                break;

            case R.id.game_sign_9 : image_9.setImageResource(R.drawable.icon_circle);
                image_9.setClickable(false);
                rc[2][2] = 0;
                if (check_win_circle())
                    dec_win(0);
                game.lastMove = 9;
                break;

            default:
                Log.d("ABCD-Switch", String.valueOf(Id));

        }
    }

    private void switchCross(int Id){

        switch (Id){
            case R.id.game_sign_1 : image_1.setImageResource(R.drawable.icon_cross);
                image_1.setClickable(false);
                rc[0][0] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 1;
                break;

            case R.id.game_sign_2 : image_2.setImageResource(R.drawable.icon_cross);
                image_2.setClickable(false);
                rc[0][1] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 2;
                break;

            case R.id.game_sign_3 : image_3.setImageResource(R.drawable.icon_cross);
                image_3.setClickable(false);
                rc[0][2] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 3;
                break;

            case R.id.game_sign_4 : image_4.setImageResource(R.drawable.icon_cross);
                image_4.setClickable(false);
                rc[1][0] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 4;
                break;

            case R.id.game_sign_5 : image_5.setImageResource(R.drawable.icon_cross);
                image_5.setClickable(false);
                rc[1][1] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 5;
                break;

            case R.id.game_sign_6 : image_6.setImageResource(R.drawable.icon_cross);
                image_6.setClickable(false);
                rc[1][2] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 6;
                break;

            case R.id.game_sign_7 : image_7.setImageResource(R.drawable.icon_cross);
                image_7.setClickable(false);
                rc[2][0] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 7;
                break;

            case R.id.game_sign_8 : image_8.setImageResource(R.drawable.icon_cross);
                image_8.setClickable(false);
                rc[2][1] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 8;
                break;

            case R.id.game_sign_9 : image_9.setImageResource(R.drawable.icon_cross);
                image_9.setClickable(false);
                rc[2][2] = 1;
                if (check_win_cross())
                    dec_win(1);
                game.lastMove = 9;
                break;

            default:
                Log.d("ABCD-Switch", String.valueOf(Id));

        }
    }

    private boolean check_win_cross(){

        if (rc[0][0]==1){
            if (rc[0][1]==1) {
                if (rc[0][2] == 1)
                    return true;
            }

            if (rc[1][0]==1) {
                if (rc[2][0] == 1)
                    return true;
            }

            if (rc[1][1]==1) {
                if (rc[2][2] == 1)
                    return true;
            }
        }//if position 1

        if(rc[0][1]==1){
            if(rc[1][1]==1)
                if(rc[2][1]==1)
                    return true;
        }//if position 2

        if(rc[0][2]==1){
            if (rc[1][1]==1) {
                if (rc[2][0] == 1)
                    return true;
            }

            if (rc[1][2]==1) {
                if (rc[2][2] == 1)
                    return true;
            }
        }//if position 3

        if(rc[1][0]==1){
            if (rc[1][1]==1)
                if(rc[1][2]==1)
                    return true;
        }//if position 4

        if (rc[2][0]==1){
            if(rc[2][1]==1)
                if(rc[2][2]==1)
                    return true;
        }//if position 7

        return false;
    }//check win cross()

    private boolean check_win_circle(){

        if (rc[0][0]==0){
            if (rc[0][1]==0) {
                if (rc[0][2] == 0)
                    return true;
            }

            if (rc[1][0]==0) {
                if (rc[2][0] == 0)
                    return true;
            }

            if (rc[1][1]==0) {
                if (rc[2][2] == 0)
                    return true;
            }
        }//if position 1

        if(rc[0][1]==0){
            if(rc[1][1]==0)
                if(rc[2][1]==0)
                    return true;
        }//if position 2

        if(rc[0][2]==0){
            if (rc[1][1]==0) {
                if (rc[2][0] == 0)
                    return true;
            }

            if (rc[1][2]==0) {
                if (rc[2][2]==0)
                    return true;
            }
        }//if position 3

        if(rc[1][0]==0){
            if (rc[1][1]==0)
                if(rc[1][2]==0)
                    return true;
        }//if position 4

        if (rc[2][0]==0){
            if(rc[2][1]==0)
                if(rc[2][2]==0)
                    return true;
        }//if position 7

        return false;
    }//check win cross()

    private void dec_win(int a){

        disableClicks();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.layout_alert_win, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        TextView win_t = (TextView) promptView.findViewById(R.id.win_text);

        if (a==p1_icon) {
            win_t.setText("Player 1 Wins!");
        }
        else {
            win_t.setText("Player 2 Wins!");
        }

        Button btnAdd1 = (Button) promptView.findViewById(R.id.win_bt_1);

        Button btnAdd2 = (Button) promptView.findViewById(R.id.win_bt_2);

        btnAdd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Refresh this activity.....Maybe use "onResume()".
            }
        });

        btnAdd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        alertD.setView(promptView);
        alertD.show();

    }//dec_win()

    private void disableClicks(){

        image_1.setClickable(false);
        image_2.setClickable(false);
        image_3.setClickable(false);
        image_4.setClickable(false);
        image_5.setClickable(false);
        image_6.setClickable(false);
        image_7.setClickable(false);
        image_8.setClickable(false);
        image_9.setClickable(false);

    }

    private void enableClicks(){

        if(rc[0][0] == 5){
            image_1.setClickable(true);
        }
        if(rc[0][1] == 5){
            image_2.setClickable(true);
        }
        if(rc[0][2] == 5){
            image_3.setClickable(true);
        }
        if(rc[1][0] == 5){
            image_4.setClickable(true);
        }
        if(rc[1][1] == 5){
            image_5.setClickable(true);
        }
        if(rc[1][2] == 5){
            image_6.setClickable(true);
        }
        if(rc[2][0] == 5){
            image_7.setClickable(true);
        }
        if(rc[2][1] == 5){
            image_8.setClickable(true);
        }
        if(rc[2][2] == 5){
            image_9.setClickable(true);
        }

    }//enableClicks()

}//GameAlt Activity
