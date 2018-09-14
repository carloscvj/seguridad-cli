/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */
package es.cvjpy.seguridad;

import es.cvjpy.CliPro;
import es.cvjpy.PrincipalPro;
import es.cvjpy.aplicacion.VersionAlfa;

/**
 *
 * @author carlos
 */
public class UsuarioLoginCli implements CliPro {

    private PrincipalPro principalPro;
    private UsuarioLoginPro usuarioLoginPro;
    private Usuario usuario;

    protected UsuarioLoginPro getLoginPro() throws Exception {
        if (usuarioLoginPro == null) {
            usuarioLoginPro = (UsuarioLoginPro) getPrincipalPro().getProgramaServidor(UsuarioLoginPro.class);
        }
        return usuarioLoginPro;
    }

    @Override
    public PrincipalPro getPrincipalPro() {
        return principalPro;
    }

    @Override
    public void setPrincipalPro(PrincipalPro principalPro) {
        this.principalPro = principalPro;
    }

    public boolean isLoginado() {
        return usuario != null;
    }

    public String getUsuarioId() throws Exception {
        if (isLoginado()) {
            return getUsuario().getId().trim();
        }
        return getPrincipalPro().getUsuarioRemoto();
    }

    public boolean comprobar(String userid, char[] passwd) throws Exception {
        String clave = new String(passwd);
        Usuario miUser = getLoginPro().getUsuario(userid);
        if (miUser != null) {
            if (miUser.getClave().equals(clave)) {
                setUsuario(miUser);
                getPrincipalPro().setUsuarioRemoto(miUser.getId().trim());
                getPrincipalPro().setPreferencias(null);//Pa que se vuelvan a cargar con el nuevo user.
                return true;
            }
        }
        return false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public VersionAlfa getVersionAlfa() throws Exception {
        return getPrincipalPro().getConfiguracionPro().getConfiguracion().getVersionAlfa();       
    }

}
