package com.jeff.connect3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    public void dropin(View view){
        // 0 = yellow ; 1 = red
        ImageView counter = (ImageView) view;

        //2 means unplayed
        int[][] winningPositions = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };

        System.out.println(counter.getTag().toString());
        Log.i("gameState", Arrays.toString(gameState));
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive){
            //Check who's on turn
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }else if (activePlayer == 1){
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            gameState[tappedCounter] = activePlayer;

            //Animate.
            counter.setTranslationY(-1000f);
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            //Check if someone has one.
            for(int[] winningPosition : winningPositions ){
                if (gameState[winningPosition[0]]== gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]]== gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                            //Someone has won.
                            gameIsActive = false;
                            String winner = "Red" ;

                            if (gameState[winningPosition[0]] == 1){
                                winner = "Yellow";
                            }

                            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                            winnerMessage.setText(winner + " has won");

                            LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                            playAgainLayout.setVisibility(View.VISIBLE);


                }else{
                     boolean gameIsOver = true;
                     for (int counterState : gameState){
                         if (counterState == 2) gameIsOver= false;
                     }

                     if (gameIsOver){
                         TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                         winnerMessage.setText(" it's a draw");

                         LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                         playAgainLayout.setVisibility(View.VISIBLE);
                     }
                }
            }
        }
    }

    public void playAgain(View view){
        gameIsActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i = 0; i < gameState.length; i++){
            gameState[i]=2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int  i = 0; i <gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
