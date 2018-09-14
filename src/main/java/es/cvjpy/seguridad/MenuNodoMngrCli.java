/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */
package es.cvjpy.seguridad;

import es.cvjpy.InterCambioCliPro;
import es.cvjpy.MngrCliPro;
import es.cvjpy.MngrInterCliAbs;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class MenuNodoMngrCli extends MngrInterCliAbs<MenuNodo, Rol> implements Serializable {

    private MenuNodoMngrPro menuNodoMngrPro;

    public MenuNodoMngrCli() {
    }

    @Override
    protected MenuNodoMngrPro getMngr() throws Exception {
        if (menuNodoMngrPro == null) {
            menuNodoMngrPro = (MenuNodoMngrPro) getPrincipalPro().getProgramaServidor(MenuNodoMngrPro.class);
        }
        return menuNodoMngrPro;

    }

    @Override
    protected List<Rol> getTodos() throws Exception {
        List<Rol> roles = getMngr().getRoles();
        return roles;
    }

    @Override
    protected InterCambioCliPro<Rol> crearIntercambio() {
        InterCambioCliPro<Rol> ret = new RolesIntercambio();
        ret.setDestino(getActual().getRoles());
        return ret;
    }

    public MngrCliPro<MenuNodo> getMngrCli() {
        return this;
    }

    @Override
    public void keyCambiada() {
        try {

            MenuNodo cosa = getEntidad(getActual().getId());
            if (cosa != null) {
                setActual(getMngr().ponteEnEste(cosa));
                setNuevo(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(MenuNodoMngrCli.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void guardar() throws Exception {
        getActual().setRoles(getIntercambio().getDestino());
        super.guardar();
    }

    public List<Rol> getPermitidos(String deste) throws Exception {
        List<Rol> ret = getMngr().getPermitidos(deste);
        return ret;
    }
}
