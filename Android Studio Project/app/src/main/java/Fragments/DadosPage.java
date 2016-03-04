package Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arturgoms.spectro.PopNew;
import com.arturgoms.spectro.PopOpen;
import com.arturgoms.spectro.R;

/**
 * Created by arturgoms on 22/02/16.
 */
public class DadosPage extends Fragment {

    private Button btnEnviar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dados_page,container,false);
        btnEnviar = (Button) rootView.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null, chooser=null;
                intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String [] to={"arturgomesomatos@gmail.com", "flashballclube@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Spectro App - Assunto");
                intent.putExtra(Intent.EXTRA_TEXT, "Coloque sua mensagem aqui :)");
                intent.setType("message/rfc822");
                chooser=Intent.createChooser(intent, "Enviar email");
                startActivity(chooser);


            }
        });

        return rootView;

    }
    public void process(View view){
        Intent intent=null, chooser=null;
        if(view.getId()==R.id.btnEnviar)
        {
            intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String [] to={"arturgomesomatos@gmail.com", "flashballclube@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Spectro App - Sugestao");
            intent.putExtra(Intent.EXTRA_TEXT, "Coloque sua mensagem aqui :)");
            intent.setType("message/rfc822");
            chooser=Intent.createChooser(intent, "Enviar email");
            startActivity(chooser);

        }
    }
}
