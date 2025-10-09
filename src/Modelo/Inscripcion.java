package Modelo;

import Modelo.Alumno;
import Modelo.Materia;

public class Inscripcion {

    private int idInscripto;
    private int nota;
    private Materia materia;
    private Alumno alumno;

    public Inscripcion(int nota, Materia materia, Alumno alumno) {
        this.nota = nota;
        this.materia = materia;
        this.alumno = alumno;
    }

    public Inscripcion() {
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public int getIdInscripto() {
        return idInscripto;
    }

    public void setIdInscripto(int idInscripto) {
        this.idInscripto = idInscripto;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

}
