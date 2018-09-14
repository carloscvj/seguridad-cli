/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */
package es.cvjpy.seguridad;

import es.cvjpy.MngrInterCliAbs;
import es.cvjpy.InterCambioCliPro;
import es.cvjpy.MngrCliPro;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class UsuarioMngrCli extends MngrInterCliAbs<Usuario, Rol> implements Serializable {

    private UsuarioMngrPro usuarioMngrPro;

    public UsuarioMngrCli() {
    }

    @Override
    protected UsuarioMngrPro getMngr() throws Exception {
        if (usuarioMngrPro == null) {
            usuarioMngrPro = (UsuarioMngrPro) getPrincipalPro().getProgramaServidor(UsuarioMngrPro.class);
        }
        return usuarioMngrPro;
    }

    @Override
    protected List<Rol> getTodos() throws Exception {
        List<Rol> inter = getMngr().getRoles();
        return inter;
    }

    @Override
    protected InterCambioCliPro<Rol> crearIntercambio() {
        InterCambioCliPro<Rol> ret = new RolesIntercambio();
        ret.setDestino(getActual().getRoles());
        return ret;
    }

    public MngrCliPro<Usuario> getMngrCli() {
        return this;
    }

    @Override
    public void keyCambiada() {
        try {

            Usuario cosa = getEntidad(getActual().getId());
            if (cosa != null) {
                setActual(getMngr().ponteEnEste(cosa));
                setNuevo(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMngrCli.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void guardar() throws Exception {
        getActual().setRoles(getIntercambio().getDestino());
        super.guardar();
    }

}
