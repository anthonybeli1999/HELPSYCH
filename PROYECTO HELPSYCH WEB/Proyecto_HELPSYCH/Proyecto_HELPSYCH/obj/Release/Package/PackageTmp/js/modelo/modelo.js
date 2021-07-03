/*
 * Modelo
 */
var Modelo = function() {
  this.preguntas = [];
  //this.ultimoId = 0;

  //inicializacion de eventos
  this.preguntaAgregada = new Evento(this);
  // Guia 1 paso 3, evento de preguntaEliminada
  this.preguntaBorrada = new Evento(this);
  this.preguntaEditada = new Evento(this);
  this.votoAgregado = new Evento(this);
  this.preguntasTodasBorradas = new Evento(this);

  //Ejecutamos la funcion verificarLocalStorage()
  this.verificarLocalStorage();
};

Modelo.prototype = {
  //se obtiene el id más grande asignado a una pregunta
  //Completado
  obtenerUltimoId: function() {
    var mayorId = -1;

    for (var i=0; i<this.preguntas.length; i++){
      if (this.preguntas[i].id > mayorId){
        mayorId = this.preguntas[i].id;
      };
    };
    return mayorId;
  },

  obtenerIndice: function(id) {

    for (i=0; i < this.preguntas.length; i++){
      if (this.preguntas[i].id === id) {
        return i;
      };
    };
  },

  //se agrega una pregunta dado un nombre y sus respuestas
  agregarPregunta: function (nombre, respuestas) {
    var id = this.obtenerUltimoId();
    id ++;
    var nuevaPregunta = {'textoPregunta': nombre, 'id': id, 'cantidadPorRespuesta': respuestas};
    this.preguntas.push (nuevaPregunta);
    this.guardar();
    this.preguntaAgregada.notificar('Pregunta agregada');
  },

  borrarPregunta: function (id) {
    this.preguntas = this.preguntas.filter(function(pregunta) { 
      return pregunta.id != id; 
    });
    this.guardar();
    this.preguntaBorrada.notificar('Pregunta borrada');
  },

  editarPregunta: function (id, nuevaPregunta) {
    var preguntaReemplazar = this.obtenerPregunta(id);
    preguntaReemplazar.textoPregunta = nuevaPregunta;

    this.preguntas
    .splice(this.preguntas
    .indexOf(this.obtenerPregunta(id)), 1, preguntaReemplazar);

    this.guardar();
    this.preguntaEditada.notificar('Pregunta editada');
  },

  borrarTodasPreguntas: function() {
    this.preguntas = [];
    this.guardar();
    this.preguntasTodasBorradas.notificar('Preguntas borradas (todas)');
  },

  obtenerPregunta: function(valor) {
    var identificador;

    if (typeof valor == 'number'){
      identificador = 'id';
    }
    else {
      identificador = 'textoPregunta'
    }
    for (var i= 0; i < this.preguntas.length; i++) {
      if (this.preguntas[i][identificador] === valor){
        return this.preguntas[i];
      }
    }
    throw new Error("La pregunta no existe");
  },

  agregarVoto: function(nombrePregunta, respuestaSeleccionada){
    this.preguntas.forEach(function(pregunta){
      if(pregunta.textoPregunta  === nombrePregunta){
        pregunta.cantidadPorRespuesta.forEach(function(respuesta){
          if(respuesta.textoRespuesta === respuestaSeleccionada){
            respuesta.cantidad +=1;
          }
        })
      }
    }); 
    this.guardar();
    this.votoAgregado.notificar('Voto agregado');
  },
   
  reloadLocalStorage: function(){
    localStorage.setItem('preguntas', JSON.stringify([]) );
  },

  verificarLocalStorage: function(){
    if (localStorage.getItem('preguntas') !== null) {
      this.preguntas = JSON.parse(localStorage.getItem('preguntas'));
    }
  },

  //se guardan las preguntas
  guardar: function(){
    localStorage.setItem('preguntas', JSON.stringify(this.preguntas));
  },
};
