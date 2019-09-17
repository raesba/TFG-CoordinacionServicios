package com.raesba.tfg_coordinacionservicios.utils;

public final class Constantes {

    public static final String FIREBASE_USUARIOS_KEY = "usuarios";
    public static final String FIREBASE_USUARIOS_UID = "uid";
    public static final String FIREBASE_USUARIOS_EMAIL = "email";
    public static final String FIREBASE_USUARIOS_TIPOUSUARIO = "tipo_usuario";
    public static final String FIREBASE_USUARIOS_VERIFICADO = "verificado";

    public static final String FIREBASE_PROVEEDORES_KEY = "proveedores";
    public static final String FIREBASE_PROVEEDORES_UID = "uid";
    public static final String FIREBASE_PROVEEDORES_NOMBRE = "nombre";
    public static final String FIREBASE_PROVEEDORES_DNI = "dni";
    public static final String FIREBASE_PROVEEDORES_EMAIL = "email";
    public static final String FIREBASE_PROVEEDORES_DIRECCION = "direccion";
    public static final String FIREBASE_PROVEEDORES_POBLACION = "poblacion";
    public static final String FIREBASE_PROVEEDORES_PROVINCIA = "provincia";
    public static final String FIREBASE_PROVEEDORES_TELEFONOFIJO = "telefonoFijo";
    public static final String FIREBASE_PROVEEDORES_MOVIL = "movil";
    public static final String FIREBASE_PROVEEDORES_PROFESION = "profesion";
    public static final String FIREBASE_PROVEEDORES_PRECIOHORA = "precioHora";
    public static final String FIREBASE_PROVEEDORES_DESCRIPCION = "descripcion";
    public static final String FIREBASE_PROVEEDORES_TIPOUSUARIO = "tipo_usuario";
    public static final String FIREBASE_PROVEEDORES_VERIFICADO = "verificado";
    public static final String FIREBASE_PROVEEDORES_DISPOSICIONES = "disposiciones";

    public static final String FIREBASE_EMPRESAS_KEY = "empresas";
    public static final String FIREBASE_EMPRESAS_UID = "uid";
    public static final String FIREBASE_EMPRESAS_RAZONSOCIAL = "razonSocial";
    public static final String FIREBASE_EMPRESAS_CIF = "cif";
    public static final String FIREBASE_EMPRESAS_EMAIL = "email";
    public static final String FIREBASE_EMPRESAS_DIRECCION = "direccion";
    public static final String FIREBASE_EMPRESAS_POBLACION = "poblacion";
    public static final String FIREBASE_EMPRESAS_PROVINCIA = "provincia";
    public static final String FIREBASE_EMPRESAS_TELEFONOFIJO = "telefonoFijo";
    public static final String FIREBASE_EMPRESAS_MOVIL = "movil";
    public static final String FIREBASE_EMPRESAS_TIPOUSUARIO = "tipo_usuario";
    public static final String FIREBASE_EMPRESAS_VERIFICADO = "verificado";

    public static final String FIREBASE_CONFIG_KEY = "config";
    public static final String FIREBASE_PROFESIONES_KEY = "profesiones";

    public static final String FIREBASE_TRANSACCIONES_KEY = "transacciones";
    public static final String FIREBASE_TRANSACCIONES_ACEPTADO = "aceptado";
    public static final String FIREBASE_TRANSACCIONES_UID_EMPRESA = "uidEmpresa";
    public static final String FIREBASE_TRANSACCIONES_UID_PROVEEDOR = "uidProveedor";
    public static final String FIREBASE_TRANSACCIONES_ESTADO_TRANSACCION = "estadoTransaccion";
//    public static final String FIREBASE_TRANSACCIONES_EMAIL = "email";
//    public static final String FIREBASE_TRANSACCIONES_DIRECCION = "direccion";
//    public static final String FIREBASE_TRANSACCIONES_POBLACION = "poblacion";
//    public static final String FIREBASE_TRANSACCIONES_PROVINCIA = "provincia";
//    public static final String FIREBASE_TRANSACCIONES_TELEFONOFIJO = "telefonoFijo";
//    public static final String FIREBASE_TRANSACCIONES_MOVIL = "movil";
//    public static final String FIREBASE_TRANSACCIONES_TIPOUSUARIO = "tipo_usuario";
//    public static final String FIREBASE_TRANSACCIONES_VERIFICADO = "verificado";

    public static final String FIREBASE_DISPOSICION_KEY = "disposiciones";
    public static final String FIREBASE_DISPOSICION_UID_PROVEEDOR = "uidProveedor";

    //Errores
    public static final String ERROR_LOGIN = "Error al hacer el login";
    public static final String ERROR_CAMPO_VACIO = "Los campos no pueden estar vacíos";
    public static final String ERROR_CANCELAR_DIALOGO = "Registro cancelado";
    public static final String ERROR_LECTURA_BBDD = "Error al leer de la base de datos";
    public static final String ERROR_CANCELAR_BBDD = "Petición a la base de datos cancelada";
    public static final String ERROR_ESCRITURA_BBDD_USUARIOS = "Error al escribir en la base de datos de usuarios";
    public static final String ERROR_ESCRITURA_BBDD_PROVEEDOR_EMPRESA = "Error al escribir en la base de datos de proveedor/empresa";
    public static final String ERROR_TIPOUSUARIO_NO_VALIDO = "Error por tipo de usuario no válido";
    public static final String ERROR_ESCRITURA_TRANSACCION = "No se ha podido escribir la transacción";
    public static final String ERROR_ESCRITURA_DISPOSICION = "No se ha podido escribir la disposición/disposiciones";

    //Mensajes
    public static final String MSG_OPCION_INVALIDA ="Opción no válida";
    public static final String MSG_REDIRIGIENDO ="Redirigiendo...";
    public static final String MSG_ESPERA ="Espere un momento, por favor...";
    public static final String MSG_PROCESANDO ="EN CONSTRUCCIÓN.....";
    public static final String MSG_GUARDADO ="Datos guardados";
    public static final String MSG_TRANSACCION_ENVIADA = "Transacción enviada con éxito";
    public static final String MSG_TRANSACCION_ACTUALIZADA = "Transacción actualizada con éxito";
    public static final String MSG_DISPOSICION_GUARDADA = "Disposición guardada con éxito";
    public static final String MSG_SIN_DISPOSICIONES = "No hay disposiciones para este proveedor";


    //LoginActivity
    public static final String PREGUNTA_QUIEN = "¿Eres empresa o proveedor?";
    public static final String DIALOGO_EMPRESA = "SOY EMPRESA";
    public static final String DIALOGO_PROVEEDOR = "SOY PROVEEDOR";

    //Extras
    public final static String EXTRA_PROVEEDOR_UID = "proveedorUid";
    public final static String EXTRA_PROVEEDOR = "proveedor";
    public final static String EXTRA_EMPRESA_UID = "empresaUid";
    public final static String EXTRA_EMPRESA = "empresa";
    public static final String EXTRA_TIPO_USUARIO = "tipo_usuario";
    public static final String EXTRA_TRANSACCION_ID = "transaccion_id";
    public static final String EXTRA_DISPOSICION_ID = "disposicion_id";
    public static final String EXTRA_DIA_FILTRADO = "dia_filtrado";

    // ProveedorDetalleActivity
    public static final String TITULO_DESCRIPCION = "DESCRIPCION" ;
    public static final String PISTA_DESCRIPCION = "Indica tu descripción";
    public static final String PISTA_DESCRIPCION_VACIA = "No hay descripción";
    public static final String DIALOGO_OK = "OK";
    public static final String DIALOGO_GUARDAR = "GUARDAR";
    public static final String DIALOGO_CANCELAR = "CANCELAR";

    public static final int USUARIO_TIPO_EMPRESA = 0;
    public static final int USUARIO_TIPO_PROVEEDOR = 1;

    // Varios

    public static final int TRANSACCION_ESTADO_PENDIENTE = 0;
    public static final int TRANSACCION_ESTADO_ACEPTADA = 1;
    public static final int TRANSACCION_ESTADO_RECHAZADA = 2;
    public static final int TRANSACCION_ESTADO_CANCELADA = 3;

    public static final String PREGUNTA_DISPONIBLE = "¿Está disponible el día seleccionado?";
    public static final String DIALOGO_DISPONIBLE = "Disponible";
    public static final String DIALOGO_NO_DISPONIBLE = "Ocupado";
    public static final String DIALOGO_LOPD = "Continuando con el registro, acepta la política de uso y los términos y condiciones";

    public static final String PREGUNTA_BAJA = "¿Está seguro de eliminar la cuenta?";
    public static final String MENSAJE_BAJA = "Esta acción no se puede deshacer.";
    public static final String DIALOGO_BORRAR = "Borrar";

    public static final String PREGUNTA_FILTRADO = "Seleccione las transacciones a mostrar";
    public static final String DIALOGO_FILTRAR = "Filtrar";

    public static final String TRANSANCIONES_ACEPTADAS = "Aceptadas";
    public static final String TRANSANCIONES_RECHAZADAS = "Rechazadas";
    public static final String TRANSANCIONES_CANCELADAS = "Canceladas";
    public static final String TRANSANCIONES_PENDIENTES = "Pendientes";


    public static final String MSG_LOPD = "TÉRMINOS Y CONDICIONES DE USO APP\n" +
            "\n" +
            "1. Estos Términos y Condiciones de Uso regulan las reglas a que se sujeta la utilización de la APP Coordinación de Servicios (en adelante, la APP), que puede descargarse desde el repositorio https://github.com/raesba/TFG-CoordinacionServicios. La descarga o utilización de la APP atribuye la condición de Usuario a quien lo haga e implica la aceptación de todas las condiciones incluidas en este documento y en la Política de Privacidad y el Aviso Legal de dicha página Web. El Usuario debería leer estas condiciones cada vez que utilice la APP, ya que podrían ser modificadas en lo sucesivo. \n" +
            "\n" +
            "2. Únicamente los Usuarios expresamente autorizados por Rafael Espejo podrán acceder a la descarga y uso de la APP. Los Usuarios que no dispongan de autorización, no podrán acceder a dicho contenido. \n" +
            "\n" +
            "3. Cargos: eres responsable del pago de todos los costes o gastos en los que incurras como resultado de descargar y usar la Aplicación de Rafael Espejo, incluido cualquier cargo de red de operador o itinerancia. Consulta con tu proveedor de servicios los detalles al respecto. \n" +
            "\n" +
            "4. Estadísticas anónimas: Rafael Espejo se reserva el derecho a realizar un seguimiento de tu actividad en la Aplicación de y a informar de ello a nuestros proveedores de servicios estadísticos de terceros. Todo ello de forma anónima. \n" +
            "\n" +
            "5. Protección de tu información personal: queremos ayudarte a llevar a cabo todos los pasos necesarios para proteger tu privacidad e información. Consulta la Política de privacidad de Rafael Espejo y los avisos sobre privacidad de la Aplicación para conocer qué tipo de información recopilamos y las medidas que tomamos para proteger tu información personal. \n" +
            "\n" +
            "6. Queda prohibido alterar o modificar ninguna parte de la APP a de los contenidas de la misma, eludir, desactivar o manipular de cualquier otra forma (o tratar de eludir, desactivar o manipular) las funciones de seguridad u otras funciones del programa y utilizar la APP o sus contenidos para un fin comercial o publicitario. Queda prohibido el uso de la APP con la finalidad de lesionar bienes, derechos o intereses de Rafael Espejo o de terceros. Queda igualmente prohibido realizar cualquier otro uso que altere, dañe o inutilice las redes, servidores, equipos, productos y programas informáticos de Rafael Espejo o de terceros. \n" +
            "\n" +
            "7. La APP y sus contenidos (textos, fotografías, gráficos, imágenes, tecnología, software, links, contenidos, diseño gráfico, código fuente, etc.), así como las marcas y demás signos distintivos son propiedad de Rafael Espejo o de terceros, no adquiriendo el Usuario ningún derecho sobre ellos por el mero uso de la APP. El Usuario, deberá abstenerse de: \n" +
            "\n" +
            "a) Reproducir, copiar, distribuir, poner a disposición de terceros, comunicar públicamente, transformar o modificar la APP o sus contenidos, salvo en los casos contemplados en la ley o expresamente autorizados por Rafael Espejo o por el titular de dichos derechos. \n" +
            "\n" +
            "b) Reproducir o copiar para uso privado la APP o sus contenidos, así como comunicarlos públicamente o ponerlos a disposición de terceros cuando ello conlleve su reproducción. \n" +
            "\n" +
            "c) Extraer o reutilizar todo o parte sustancial de los contenidos integrantes de la APP. \n" +
            "\n" +
            "\n" +
            "8. Con sujeción a las condiciones establecidas en el apartado anterior, Rafael Espejo concede al Usuario una licencia de uso de la APP, no exclusiva, gratuita, para uso personal, circunscrita al territorio nacional y con carácter indefinido. Dicha licencia se concede también en los mismos términos con respecto a las actualizaciones y mejoras que se realizasen en la aplicación. Dichas licencias de uso podrán ser revocadas por Rafael Espejo unilateralmente en cualquier momento, mediante la mera notificación al Usuario. \n" +
            "\n" +
            "9. Corresponde al Usuario, en todo caso, disponer de herramientas adecuadas para la detección y desinfección de programas maliciosos o cualquier otro elemento informático dañino. Rafael Espejo no se responsabiliza de los daños producidos a equipos informáticos durante el uso de la APP. Igualmente, Rafael Espejo no será responsable de los daños producidos a los Usuarios cuando dichos daños tengan su origen en fallos o desconexiones en las redes de telecomunicaciones que interrumpan el servicio. \n" +
            "\n" +
            "10. IMPORTANTE: Podemos, sin que esto suponga ninguna obligación contigo, modificar estas Condiciones de uso sin avisar en cualquier momento. Si continúas utilizando la aplicación una vez realizada cualquier modificación en estas Condiciones de uso, esa utilización continuada constituirá la aceptación por tu parte de tales modificaciones. Si no aceptas estas condiciones de uso ni aceptas quedar sujeto a ellas, no debes utilizar la aplicación ni descargar o utilizar cualquier software relacionado. El uso que hagas de la aplicación queda bajo tu única responsabilidad. No tenemos responsabilidad alguna por la eliminación o la incapacidad de almacenar o trasmitir cualquier contenido u otra información mantenida o trasmitida por la aplicación. No somos responsables de la precisión o la fiabilidad de cualquier información o consejo trasmitidos a través de la aplicación. Podemos, en cualquier momento, limitar o interrumpir tu uso a nuestra única discreción. Hasta el máximo que permite la ley, en ningún caso seremos responsables por cualquier pérdida o daño relacionados. \n" +
            "\n" +
            "11. El Usuario se compromete a hacer un uso correcto de la APP, de conformidad con la Ley, con los presentes Términos y Condiciones de Uso y con las demás reglamentos e instrucciones que, en su caso, pudieran ser de aplicación El Usuario responderá frente a Rafael Espejo y frente a terceros de cualesquiera daños o perjuicios que pudieran causarse por incumplimiento de estas obligaciones. \n" +
            "\n" +
            "12. Estos Términos y Condiciones de Uso se rigen íntegramente por la legislación española. Para la resolución de cualquier conflicto relativo a su interpretación o aplicación, el Usuario se somete expresamente a la jurisdicción de los tribunales de Madrid (España). \n";


}
