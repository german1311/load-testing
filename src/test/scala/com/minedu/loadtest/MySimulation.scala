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

  val token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTA0NDM5NTAsImp0aSI6ImFlYzI1MWZkLTNhNTAtNDI3MS1iOTRkLTJkOGQ1M2RhMDI1ZSIsInJkayI6Ijg5YTNhMDQ3ODhiMzRiZDY4YjU5Yzg5MDE2NmNiOGEzIiwidXNyIjoiMTM5MTMxNiIsInNlZCI6IjI4NjMwIiwicm9sIjoiOTUzOSIsImNybCI6IlIwMV8yNiIsImNtbCI6IjA1NDYzOTAiLCJhbngiOiIwIiwiYWd3IjoibnNpYWdpZS0yMDQ4IiwiaWRTZXNzaW9uIjoiODlhM2EwNDc4OGIzNGJkNjhiNTljODkwMTY2Y2I4YTMiLCJuYmYiOjE3MTA0NDM5NTAsImV4cCI6MTcxMDQ0NDk3MCwiaXNzIjoibnNpYWdpZS0yMDQ4IiwiYXVkIjoibWluZWR1LnNpYWdpZS52MiJ9.Tv-r6V8X_l2z8WhPzL9PN5MR6SRk_Gln5dLamhzS_uhqtGK7iX-TXiKdQ2EsVlWhaoBcDwlRrDQB0qkYxuujrKKWvxtYbjincT7YIcD7uxwiO8d9BuONiKmkE-MXfKZYXQ4C6vOnigGScBLF51wwNgBKrYIGpa-rReGhOgfQQDH85jE_667AKEzvl_mPbtBF4CYMkITXk_568S86DGLACXuIhuXGyiZI-Ovq9Pbc1mAXEnuGJdXzMycmkzFQz1MYdODqM_nGiScHOW7etYycLUdtuWrfUnHAvm2SqrzZNfHV8CpB81Zl9aDRmDjMbX5mhH_moaCOc0Wzslp4vwpUMA"
  val header_login = Map(
    "Connection" -> "keep-alive",
    "x-requested-with" -> "Fetch",
    "authority" ->"10.200.13.59:5433",
    "accept"->"application/json, text/plain, */*",
    "accept-language"->"en-US,en;q=0.9",
    "codigo-rol"->"R01_16",
    "codigo-sede"->"0546390",
    "content-type"->"application/json",
    "origin"->"https://web.dev.siagie.in.otic.pe",
    "referer"->"https://web.dev.siagie.in.otic.pe/",   
    "sec-ch-ua"->""""Chromium";v="122", "Not(A:Brand";v="24", "Microsoft Edge";v="122"""",
    "sec-ch-ua-mobile"->"?0",
    "sec-ch-ua-platform"->"Windows",
    "sec-fetch-dest"->"empty",
    "sec-fetch-mode"->"cors",
    "sec-fetch-site"->"same-site",
    "user-agent"->"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0"
    )
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
  
  val scn = scenario("BasicSimulation")
    .feed(namesData)
    // .exec(http("login")
    //   .post("/auth")
    //   .headers(header_login)
    //   .body(StringBody("""{
    //       "user": "70140602",
    //       "clave": "gvaHJfHwLgO2hlWHvPmKrdfRCjDLV6LKaUo861ygdUJGqo0Q40nwo1AMY4CgdgxnaOU2ytBD7jmIQNZle6dS1vUZvJ6f5Ku4Z5ixTqBEsC1z2vs5QCZQIfduf/kOlDpFjd0MnJCIoBDlgHYA65mz0fkBv0Hqj7J32AeMBZIpzL57PSdV9iGOmU3Ef3mET8VrSHu/kNHtZgqYTb5wkKpShw+ahxGp7zXwAOuGHeBc1kOoc33CzyHxjauNmQip3lFpPaw1T6kk9yw5q+dDilbtjPmKRp3yp/O03YJtQl4h8JkGxRd0rh935lVL9ZrdK7QMPS/Ff7f3Xtp7vdxj8jmyig==",
    //       "captcha": "",
    //       "captchaKey": ""
    //   }"""))
    //   .check(jsonPath("$.accessToken").find.saveAs("token"))
    // )
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
    // .exec(session => {
    //   val response = session("BODY").as[String]
    //   println(s"Response body: \n$response")
    //   session
    // })
    .pause(5)
    .exec(http("patch_ambiente")
      .put("/api/horas-grados/ambientes/2808/1")
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
    // .exec(session => {
    //   val response = session("BODY").as[String]
    //   println(s"Response body: \n$response")
    //   session
    // })
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
    // .exec(session => {
    //   val response = session("BODY").as[String]
    //   println(s"Response body: \n$response")
    //   session
    // })
    .pause(5)
    .exec(http("patch_seccion")
      .put("/api/horas-grados/secciones/2808/1")
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
    // .exec(session => {
    //   val response = session("BODY").as[String]
    //   println(s"Response body: \n$response")
    //   session
    // })

  setUp(
    scn.inject(
      constantConcurrentUsers(10).during(10), // 1
      rampConcurrentUsers(10).to(20).during(10) // 2
      )
      .protocols(httpProtocol)
    )    
  .maxDuration(10.minutes)
}