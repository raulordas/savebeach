package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uem.dam.sharethebeach.sharethebeach.R;

public class Acerca_De_Activity extends AppCompatActivity {

    ImageView ivFoto;
    TextView tvNombre;
    TextView tvDescripcion;
    TextView tvCorreo;
    String[] nombres;   // Array con el nombre de todos los desarrolladores
    String[] descripciones;  // Array con la descripcion de todos los desarrolladores
    int desarrolladoractual;  // Indica que desarrollador es el que se esta mostrando en cada caso
    float x1, x2;
    float y1, y2;
    String correodestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca__de_);

        ivFoto = findViewById(R.id.ivFoto);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvNombre = findViewById(R.id.tvNombre);
        nombres = new String[4];
        descripciones = new String[4];
        correodestinatario = getResources().getString(R.string.correo_desarrolladores);
        tvCorreo.setText(correodestinatario);

        rellenarArrays();  // rellenadmos los arrays de nombres y descripciones de todos los desarrolladores
        desarrolladoractual = 1;  // comenzamos pintando al desarrollador 1
        rellenarViews();    // actualizamos los views

        tvCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Al pinchar en el correo se pone azul
                tvCorreo.setTextColor(Color.BLUE);
                Intent i = new Intent(Intent.ACTION_SEND);

                //Esta línea es para que coja la aplicación de correo por defecto si hay más de una
                //en el movil
                i.setType("message/rfc822");

                //Esta línea mete un asunto al correo que vamos a enviar
                i.putExtra(Intent.EXTRA_SUBJECT, " Consulta usuario Shave the Beach");

                i.putExtra(Intent.EXTRA_EMAIL,new String[]{correodestinatario} );

                try {
                    //Linea para lanzar la petición de correo
                    startActivity(Intent.createChooser(i, "Enviando correo"));

                }catch(android.content.ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"No hay aplicacion de correo en el movil",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void rellenarViews() {
        //Dependiendo de quien es el desarrolladoractual, pone en cada imagen y
        // textView su texo e imagen correspondientes
        //En el switch según el valor del desarrolladoractual coge un recurso u otro de las fotos y la pega
        //en el ImageView
        tvNombre.setText(nombres[desarrolladoractual - 1]);
        tvDescripcion.setText(descripciones[desarrolladoractual - 1]);
        switch (desarrolladoractual) {
            case 1:
                ivFoto.setImageResource(R.drawable.miguel);
                break;
            case 2:
                ivFoto.setImageResource(R.drawable.raul);
                break;
            case 3:
                ivFoto.setImageResource(R.drawable.juan);
                break;
            case 4:
                ivFoto.setImageResource(R.drawable.andres);
                break;
        }
    }

    public void rellenarArrays() {
        //Rellenamos el array, con los nombres y las descripciones sacándolos del fichero Strings
        nombres[0] = getResources().getString(R.string.nombre_desarrollador_1);
        nombres[1] = getResources().getString(R.string.nombre_desarrollador_2);
        nombres[2] = getResources().getString(R.string.nombre_desarrollador_3);
        nombres[3] = getResources().getString(R.string.nombre_desarrollador_4);

        descripciones[0] = getResources().getString(R.string.descripcion_desarrollador_1);
        descripciones[1] = getResources().getString(R.string.descripcion_desarrollador_2);
        descripciones[2] = getResources().getString(R.string.descripcion_desarrollador_3);
        descripciones[3] = getResources().getString(R.string.descripcion_desarrollador_4);


    }

    // Metodo onTouchEvent
    //Es un swap que
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // Cuando pongo el dedo en la pantalla, me coge las cordenadas de X e Y
            case MotionEvent.ACTION_DOWN: {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            // Cuando suelto la pantalla con el dedo, me coge las nuevas cordenadas X e Y
            case MotionEvent.ACTION_UP: {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                //Si la cordenada x inicial es menor que donde la he soltado, me muevo de izquierda a derecha
                if (x1 < x2) {
                    desarrolladoractual--;
                    if (desarrolladoractual == 0) {
                        desarrolladoractual = 4;
                    }
                    rellenarViews();
                }

                // Si la cordenada x inicia es mayor que dode la he soltado, me muevo de derecha a izquierda
                if (x1 > x2) {
                    desarrolladoractual++;
                    if (desarrolladoractual == 5) {
                        desarrolladoractual = 1;
                    }
                    rellenarViews();
                }

                break;
            }
        }
        return false;
    }
}
