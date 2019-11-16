package com.example.kopapirollo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b_rock, b_scissors, b_paper;
    TextView tv_score;
    ImageView iv_ComputerChoice, iv_HomanChoice;

    int HumanScore, ComputerScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_paper = (Button) findViewById(R.id.b_paper);
        b_scissors = (Button) findViewById(R.id.b_scissors);
        b_rock = (Button) findViewById(R.id.b_rock);

        iv_ComputerChoice = (ImageView) findViewById(R.id.iv_ComputerChoice);
        iv_HomanChoice = (ImageView) findViewById(R.id.iv_HumanChoice);

        tv_score = (TextView) findViewById(R.id.tv_score);

        b_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HomanChoice.setImageResource(R.drawable.papir);
                String message = play_turn("paper");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                tv_score.setText("Score: Human: "+ Integer.toString(HumanScore) + "  Computer: "+ Integer.toString(ComputerScore));
            }
        });

        b_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HomanChoice.setImageResource(R.drawable.ko);
                String message = play_turn("rock");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                tv_score.setText("Score: Human: "+ Integer.toString(HumanScore) + "  Computer: "+ Integer.toString(ComputerScore));
            }
        });

        b_scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_HomanChoice.setImageResource(R.drawable.ollo);
                String message = play_turn("scissors");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                tv_score.setText("Score: Human: "+ Integer.toString(HumanScore) + "  Computer: "+ Integer.toString(ComputerScore));
            }
        });

    }

    public String play_turn( String player_choice) {
        String computer_choice = "";
        Random r = new Random();

        int computer_choice_number = r.nextInt(3)+1;

        if(computer_choice_number == 1){
            computer_choice = "rock";
            iv_ComputerChoice.setImageResource(R.drawable.ko);
        }

        if(computer_choice_number == 2){
            computer_choice = "scissors";
            iv_ComputerChoice.setImageResource(R.drawable.ollo);
        }

        if(computer_choice_number == 3){
            computer_choice = "paper";
            iv_ComputerChoice.setImageResource(R.drawable.papir);
        }

        if(computer_choice.equals(player_choice)) {
            return "Draw. Nobody won.";
        }

        else if(computer_choice.equals("rock") && player_choice.equals("scissors") ) {
            ComputerScore++;
            return "Rock crushes scissors. Computer wins";
        }
        else if(computer_choice.equals("rock") && player_choice.equals("paper") ) {
            HumanScore++;
            return "Paper covers rock. You win";
        }
        else if(computer_choice.equals("scissors") && player_choice.equals("rock") ) {
            HumanScore++;
            return "Rock crushes scissors. You win!";
        }
        else if(computer_choice.equals("scissors") && player_choice.equals("paper") ) {
            ComputerScore++;
            return "Scissors cuts paper. Computer wins!";
        }
        else if(computer_choice.equals("paper") && player_choice.equals("scissors") ) {
            HumanScore++;
            return "Scissors cuts paper. You win!";
        }
        else if(computer_choice.equals("paper") && player_choice.equals("rock") ) {
            ComputerScore++;
            return "Paper Covers rock. Computer wins!";
        }
        else return "Not Sure";

    }
}
