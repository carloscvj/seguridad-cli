/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */
package es.cvjpy.seguridad;

import es.cvjpy.MngrCliAbs;
import es.cvjpy.MngrCliPro;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class RolMngrCli extends MngrCliAbs<Rol> implements Serializable {

    private RolMngrPro rolMngrPro;

    public RolMngrCli() {
    }

    @Override
    protected RolMngrPro getMngr() throws Exception {
        if (rolMngrPro == null) {
            rolMngrPro = (RolMngrPro) getPrincipalPro().getProgramaServidor(RolMngrPro.class);
        }
        return rolMngrPro;
    }

    public MngrCliPro<Rol> getMngrCli() {
        return this;
    }

    @Override
    public void keyCambiada() {
        try {
            Rol cosa = getEntidad(getActual().getId());
            if (cosa != null) {
                setActual(getMngr().ponteEnEste(cosa));
                setNuevo(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(RolMngrCli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
