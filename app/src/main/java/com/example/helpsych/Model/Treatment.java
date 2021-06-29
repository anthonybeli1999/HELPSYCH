package com.example.helpsych.Model;

public class Treatment {

    String idFichaAtencion, idSesion, fechaSesion, procedimiento, recomendacion, idPaciente, idEspecialista;

    public Treatment(String idFichaAtencion, String idSesion, String fechaSesion, String procedimiento, String recomendacion, String idPaciente, String idEspecialista) {
        this.idFichaAtencion = idFichaAtencion;
        this.idSesion = idSesion;
        this.fechaSesion = fechaSesion;
        this.procedimiento = procedimiento;
        this.recomendacion = recomendacion;
        this.idPaciente = idPaciente;
        this.idEspecialista = idEspecialista;
    }

    public Treatment() {
        this.idFichaAtencion = idFichaAtencion;
        this.idSesion = idSesion;
        this.fechaSesion = fechaSesion;
        this.procedimiento = procedimiento;
        this.recomendacion = recomendacion;
        this.idPaciente = idPaciente;
        this.idEspecialista = idEspecialista;
    }

    public String getIdFichaAtencion() {
        return idFichaAtencion;
    }

    public void setIdFichaAtencion(String idFichaAtencion) {
        this.idFichaAtencion = idFichaAtencion;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(String fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(String idEspecialista) {
        this.idEspecialista = idEspecialista;
    }
}
