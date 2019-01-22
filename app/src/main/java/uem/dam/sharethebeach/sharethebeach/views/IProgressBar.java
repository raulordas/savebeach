package uem.dam.sharethebeach.sharethebeach.views;

public interface IProgressBar {

    //Muestra la barra de progreso de conexión al servidor
    public void mostrarProgressBar();

    //Cierra la barra de progreso de conexión al servidor
    public void cerrarProgressBar();

    //Durante el uso del progress bar puede ocurrir un error que queramos notificar
    public void mostrarDialogError(int idStringError);
}
