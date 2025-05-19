package br.com.fiap.Mottracker.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioPermissaoId implements Serializable {
    private Long usuarioId;
    private Long permissaoId;

    public UsuarioPermissaoId() {
    }

    public UsuarioPermissaoId(Long usuarioId, Long permissaoId) {
        this.usuarioId = usuarioId;
        this.permissaoId = permissaoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPermissaoId() {
        return permissaoId;
    }

    public void setPermissaoId(Long permissaoId) {
        this.permissaoId = permissaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioPermissaoId)) return false;
        UsuarioPermissaoId that = (UsuarioPermissaoId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(permissaoId, that.permissaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, permissaoId);
    }
}
