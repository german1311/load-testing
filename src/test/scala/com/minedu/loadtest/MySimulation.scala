package com.minedu.loadtest

// import io.gatling.core
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Random
import scala.math.Fractional.Implicits.infixFractionalOps
import scala.math.Integral.Implicits.infixIntegralOps
import scala.math.Numeric.Implicits.infixNumericOps

class MySimulation extends Simulation {
  def randomNumber() = Random.nextInt(Integer.MAX_VALUE)

  val namesData = Iterator.continually(
    // Random number will be accessible in session under variable "OrderRef"
    Map("nombres" -> s"Automation${Random.nextInt(Integer.MAX_VALUE)}")
  )

  val httpProtocol = http
    .baseUrl("https://192.168.7.106:15303")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTAzOTE4NjcsImp0aSI6IjI4YjI5OWNkLTZjYjAtNDE5Ny04YjlmLTU4OWNiOWQ3MDFjYyIsInJkayI6ImVhMWIwYjAwMGEwOTRhODA5MzIxODRiNjViYThlMzRkIiwidXNyIjoiMTM5MTMxNiIsInNlZCI6IiIsInJvbCI6IiIsImNybCI6IiIsImNtbCI6IiIsImFueCI6IiIsImFndyI6Im5zaWFnaWUtMjA0OCIsImlkU2Vzc2lvbiI6ImVhMWIwYjAwMGEwOTRhODA5MzIxODRiNjViYThlMzRkIiwibmJmIjoxNzEwMzkxODY3LCJleHAiOjE3MTAzOTI4ODcsImlzcyI6Im5zaWFnaWUtMjA0OCIsImF1ZCI6Im1pbmVkdS5zaWFnaWUudjIifQ.t8KZEgJY9_WE1RO9oAqjWHdqJvskhbQoonunaqwnl-T-Ax_qnHI64Of9Vv5z-OvawjhcrPAO8vizWDXSeb22ChyrabXyWklJyks1txt2VuXZ42rdD90rKK4BSAmk8nO0xmPiL3X52_cnAkTrQ4LGhZZvgmjyd8r3JxO4YUKutZcdw8aKbRmp-uq6WdtwYN84a_wjTiXvd7Rg2RbbGzf-7iy_2x2plZTCceK3zH_WCweTTafnUk-BtPUd-M7eK0_HP3GIDneHWxvRfwxcT-U0YJqrvKuuxAW847cDouZ5A4FT2B6SVRGY1ROBxHOpkqxNSWA3yQAU81V8nfyDi6UJVw"
  val header_accept_json = Map(
    "Connection" -> "keep-alive",
    "Authorization" -> s"Bearer ${token}",
    "x-requested-with" -> "Fetch",
    "authority" ->"192.168.7.106:15303",
    "accept"->"application/json, text/plain, */*",
    "accept-language"->"en-US,en;q=0.9",
    "codigo-rol"->"R01_16",
    "codigo-sede"->"0546390",
    "content-type"->"application/json",
    "origin"->"https://192.168.7.106:15306",
    "sec-ch-ua"->""""Chromium";v="122", "Not(A:Brand";v="24", "Microsoft Edge";v="122"""",
    "sec-ch-ua-mobile"->"?0",
    "sec-ch-ua-platform"->"Windows",
    "sec-fetch-dest"->"empty",
    "sec-fetch-mode"->"cors",
    "sec-fetch-site"->"same-site",
    "user-agent"->"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0"
    )

  val feeder = csv("brainyData.csv")

  val scn = scenario("BasicSimulation")
    .feed(namesData)
    .exec(http("post_ambientes")
      .post("/api/horas-grados/ambientes")
      .headers(header_accept_json)
      .body(StringBody("""{
          "idModuloEducativo": 2808,
          "nombre": "${nombres} Ambiente",
          "descripcion": "cualqwuier descripcion",
          "idTipoAmbiente": 146,
          "idSede": 0,
          "idUbicacion": 153,
          "idTipoUso": 156,
          "idCondicion": 159,
          "aforo": 12,
          "area": 32,
          "pabellon": "dsfsdfshdcgvxc",
          "idPiso": 163,
          "observacion": ""
      }"""))
      .check(bodyString.saveAs("BODY"))
    )
    .exec(session => {
      val response = session("BODY").as[String]
      println(s"Response body: \n$response")
      session
    })
    .pause(5)
    .exec(http("patch_ambiente")
      .patch("/api/horas-grados/ambientes/2808/1")
      .headers(header_accept_json)
      .body(StringBody("""{
        "idModuloEducativo": 2808,
        "idAmbiente": 1,
        "nombre": "AULA DE CLASE",
        "descripcion": "AL LADO IZQUIERDO DE LA DIRECCIÓN",
        "idTipoAmbiente": 146,
        "idSede": 0,
        "idUbicacion": 153,
        "idTipoUso": 157,
        "idCondicion": 161,
        "aforo": 30,
        "area": 56,
        "pabellon": "1",
        "idPiso": 163,
        "observacion": "PROFESORA MARIBEL TAPULLIMA ROJAS"
      }"""))
      .check(bodyString.saveAs("BODY"))
    )
    .exec(session => {
      val response = session("BODY").as[String]
      println(s"Response body: \n$response")
      session
    })
    .pause(5)
    .exec(http("post_secciones")
      .post("/api/horas-grados/secciones")
      .headers(header_accept_json)
      .body(StringBody("""{
          "idModuloEducativo": 2808,
          "idModeloServicioEducativo": 138,
          "idTipoEnsenanza": 2644,
          "idFase": 4378,
          "idTurno": 3108,
          "diasLaborables": [
              {
                  "id": 19615
              },
              {
                  "id": 19616
              },
              {
                  "id": 19617
              },
              {
                  "id": 19618
              },
              {
                  "id": 19619
              }
          ],
          "idAmbiente": 2,
          "nomAmbiente": "AULA",
          "nomCiclo": "CICLO III",
          "nomGrado": "PRIMERO",
          "cantidadAlumnos": 45,
          "idCiclo": 112,
          "idGrado": 16,
          "nombre": "${nombres} Seccion",
          "idTutor": "de82474d-066e-4726-a8e9-606b27c61e37",
          "idPrograma": 180,
          "observaciones": ""
      }"""))
      .check(bodyString.saveAs("BODY"))
    )
    .exec(session => {
      val response = session("BODY").as[String]
      println(s"Response body: \n$response")
      session
    })
    .pause(5)
    .exec(http("patch_seccion")
      .patch("/api/horas-grados/secciones/2808/1")
      .headers(header_accept_json)
      .body(StringBody("""{
          "idSeccion": 1,
          "idModuloEducativo": 2808,
          "idModeloServicioEducativo": 138,
          "idTipoEnsenanza": 2644,
          "idFase": 4378,
          "idTurno": 3108,
          "diasLaborables": [
              {
                  "id": 19615
              },
              {
                  "id": 19616
              },
              {
                  "id": 19617
              },
              {
                  "id": 19618
              },
              {
                  "id": 19619
              }
          ],
          "idAmbiente": 1,
          "nomAmbiente": "AULA DE CLASE",
          "nomCiclo": "CICLO III",
          "nomGrado": "PRIMERO",
          "cantidadAlumnos": 15,
          "idCiclo": 112,
          "idGrado": 16,
          "nombre": "A",
          "idTutor": "61ce3146-5dbd-4a5b-afdf-64c315940b01",
          "idPrograma": 180,
          "observaciones": ""
      }"""))
      .check(bodyString.saveAs("BODY"))
    )
    .exec(session => {
      val response = session("BODY").as[String]
      println(s"Response body: \n$response")
      session
    })

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}