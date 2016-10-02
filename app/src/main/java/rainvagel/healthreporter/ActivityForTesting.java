package rainvagel.healthreporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityForTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_testing);
    }

    public void goToClients(View v){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
    public void goToTests(View v){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
    public void goToCategories(View v){
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }
}
