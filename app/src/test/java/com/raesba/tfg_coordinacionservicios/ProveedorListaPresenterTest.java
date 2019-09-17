package com.raesba.tfg_coordinacionservicios;

import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedoresCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.proveedorlista.ProveedorListaContract;
import com.raesba.tfg_coordinacionservicios.ui.proveedorlista.ProveedorListaPresenter;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProveedorListaPresenterTest {

    @Mock
    private ProveedorListaContract.Activity view;

    @Mock
    private DatabaseManager databaseManager;

    private ProveedorListaPresenter presenter;

    @Captor
    ArgumentCaptor<GetProveedoresCallback> argumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Cogemos la referencia real de la clasa que queremos probar
        presenter = new ProveedorListaPresenter(databaseManager);
    }

    @Test
    public void loadProveedorList() {
        presenter.vistaActiva(view);
        presenter.getProveedores(null);

        verify(databaseManager).getProveedores((String) isNull(), argumentCaptor.capture());
        argumentCaptor.getValue().onSuccess(falsearLista());

        verify(view, times(2)).setProgessBar(anyBoolean());

        ArgumentCaptor<ArrayList> entityArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(view).mostrarProveedores(entityArgumentCaptor.capture());

        assertEquals(10, entityArgumentCaptor.getValue().size());
    }

    @Test
    public void loadProveedorListError() {
        presenter.vistaActiva(view);
        presenter.getProveedores(null);

        verify(databaseManager).getProveedores((String) isNull(), argumentCaptor.capture());
        argumentCaptor.getValue().onError(Constantes.ERROR_LECTURA_BBDD);

        verify(view, times(2)).setProgessBar(anyBoolean());

        ArgumentCaptor<String> entityArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(view).mostrarToast(entityArgumentCaptor.capture());

        // Para estar seguros que no estamos mostrando información
        verify(view, times(0)).mostrarProveedores(any(ArrayList.class));

        assertEquals(Constantes.ERROR_LECTURA_BBDD, entityArgumentCaptor.getValue());
    }

    private ArrayList<Proveedor> falsearLista() {
        ArrayList<Proveedor> proveedores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setProfesion("Profesión " + i);
            proveedor.setDisposiciones(new HashMap<String, Boolean>());
            proveedor.setDni("DNI " + i);
            proveedor.setNombre("Nombre " + i);
            proveedor.setPrecioHora((float) (i * 1.2));
            proveedor.setDescripcion("Descripción " + i);

            proveedores.add(proveedor);
        }

        return proveedores;
    }
}