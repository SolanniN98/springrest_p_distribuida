package com.distribuida;

import com.distribuida.config.AppConfig;
import com.distribuida.db.Persona;
import com.distribuida.service.IServicioPersona;
import com.google.gson.Gson;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static IServicioPersona servicio;
    static AnnotationConfigApplicationContext context;
    static String ingresarPersona(Request req, Response res){
        servicio= context.getBean(IServicioPersona.class);
        res.type("application/json");
        String jsonPersona = req.body();
        Persona persona= new Gson().fromJson(jsonPersona, Persona.class);
        servicio.insertar(persona);

        if(persona==null){
           halt(404, "ERROR AL INSERTAR");
        }

        return "Persona ingresada";
    }

    static String actualizarPersona(Request req, Response res){
        res.type("application/json");
        String jsonPersona = req.body();
        servicio=context.getBean(IServicioPersona.class);
        Persona persona= new Gson().fromJson(jsonPersona, Persona.class);
        servicio.actualizar(persona);
        return "Persona actualizada";
    }

    static String eliminarPersona(Request req, Response res){
        res.type("application/json");
        String _id= req.params(":id");
        servicio=context.getBean(IServicioPersona.class);
        servicio.eliminar(Integer.valueOf(_id));
        return "Persona eliminada";
    }

    static Persona buscarPersona(Request req, Response res){
        res.type("application/json");
        String _id= req.params(":id");
        servicio=context.getBean(IServicioPersona.class);
        return servicio.buscarPersona(Integer.valueOf(_id));
    }

    static List<Persona> buscarPersonas(Request req, Response res){
        res.type("application/json");
        servicio=context.getBean(IServicioPersona.class);
        return servicio.buscarTodos();
    }

    public static void main(String[] args) {
       context = new AnnotationConfigApplicationContext(AppConfig.class);
         servicio= context.getBean(IServicioPersona.class);
         Gson gson = new Gson();
         port(8080);

        get("/personas", Main::buscarPersonas, gson::toJson);
        get("/personas/:id", Main::buscarPersona, gson::toJson);
        post("/personas", Main::ingresarPersona, gson::toJson);
        put("/personas", Main::actualizarPersona, gson::toJson);
        delete("/personas/:id", Main::eliminarPersona, gson::toJson);

    }
}