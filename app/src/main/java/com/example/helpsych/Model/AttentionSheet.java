package com.example.helpsych.Model;

public class AttentionSheet {

    String idFichaAtencion, nombresApellidosPaciente, fechaEmperejamiento, motivoConsulta, antecedentes, especialista, idPaciente, idEspecialista;

    public AttentionSheet(String idFichaAtencion, String nombresApellidosPaciente, String fechaEmperejamiento, String motivoConsulta, String antecedentes, String especialista, String idPaciente, String idEspecialista) {
        this.idFichaAtencion = idFichaAtencion;
        this.nombresApellidosPaciente = nombresApellidosPaciente;
        this.fechaEmperejamiento = fechaEmperejamiento;
        this.motivoConsulta = motivoConsulta;
        this.antecedentes = antecedentes;
        this.especialista = especialista;
        this.idPaciente = idPaciente;
        this.idEspecialista = idEspecialista;
    }

    public AttentionSheet() {
        this.idFichaAtencion = idFichaAtencion;
        this.nombresApellidosPaciente = nombresApellidosPaciente;
        this.fechaEmperejamiento = fechaEmperejamiento;
        this.motivoConsulta = motivoConsulta;
        this.antecedentes = antecedentes;
        this.especialista = especialista;
        this.idPaciente = idPaciente;
        this.idEspecialista = idEspecialista;
    }

    public String getIdFichaAtencion() {
        return idFichaAtencion;
    }

    public void setIdFichaAtencion(String idFichaAtencion) {
        this.idFichaAtencion = idFichaAtencion;
    }

    public String getNombresApellidosPaciente() {
        return nombresApellidosPaciente;
    }

    public void setNombresApellidosPaciente(String nombresApellidosPaciente) {
        this.nombresApellidosPaciente = nombresApellidosPaciente;
    }

    public String getFechaEmperejamiento() {
        return fechaEmperejamiento;
    }

    public void setFechaEmperejamiento(String fechaEmperejamiento) {
        this.fechaEmperejamiento = fechaEmperejamiento;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getEspecialista() {
        return especialista;
    }

    public void setEspecialista(String especialista) {
        this.especialista = especialista;
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
