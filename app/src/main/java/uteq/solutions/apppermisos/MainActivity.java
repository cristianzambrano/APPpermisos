package uteq.solutions.apppermisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> permisosNoAprobados;
    Button btCamera, btArchivo, btCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> permisos_requeridos = new ArrayList<String>();
        permisos_requeridos.add(Manifest.permission.CAMERA);
        permisos_requeridos.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        permisos_requeridos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permisos_requeridos.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permisos_requeridos.add(Manifest.permission.READ_CALENDAR);
        permisos_requeridos.add(Manifest.permission.WRITE_CALENDAR);
        btCamera = this.findViewById(R.id.btCamara);
        btArchivo = this.findViewById(R.id.btArchivo);
        btCalendario = this.findViewById(R.id.btCalendario);

        permisosNoAprobados  = getPermisosNoAprobados(permisos_requeridos);
    }
    public void onrequestpermission (View view){
        requestPermissions(permisosNoAprobados.toArray(new String[permisosNoAprobados.size()]),
                100);

        //shouldShowRequestPermissionRationale()
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int i=0; i<permissions.length; i++){
            if(permissions[i].equals(Manifest.permission.CAMERA)){
                btCamera.setEnabled(grantResults[i] == PackageManager.PERMISSION_GRANTED);
            } else if(permissions[i].equals(Manifest.permission.MANAGE_EXTERNAL_STORAGE) ||
                    permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
            ) {
                btArchivo.setEnabled(grantResults[i] == PackageManager.PERMISSION_GRANTED);
            } else if (permissions[i].equals(Manifest.permission.READ_CALENDAR)
                    || permissions[i].equals(Manifest.permission.WRITE_CALENDAR)){
                btCalendario.setEnabled(grantResults[i] == PackageManager.PERMISSION_GRANTED);
            }
        }
    }

    public ArrayList<String> getPermisosNoAprobados(ArrayList<String>  listaPermisos) {
        ArrayList<String> list = new ArrayList<String>();
        Boolean habilitado;


        if (Build.VERSION.SDK_INT >= 23)
            for(String permiso: listaPermisos) {
                if (checkSelfPermission(permiso) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permiso);
                    habilitado = false;
                }else
                    habilitado=true;

                if(permiso.equals(Manifest.permission.CAMERA))
                    btCamera.setEnabled(habilitado);
                else if (permiso.equals(Manifest.permission.MANAGE_EXTERNAL_STORAGE)  ||
                        permiso.equals(Manifest.permission.READ_EXTERNAL_STORAGE)  ||
                         permiso.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                   btArchivo.setEnabled(habilitado);
                else if (permiso.equals(Manifest.permission.READ_CALENDAR)
                        || permiso.equals(Manifest.permission.WRITE_CALENDAR))
                    btCalendario.setEnabled(habilitado);

            }


        return list;
    }


}