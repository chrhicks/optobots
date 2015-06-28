package io.photoop.optobot.model

import play.api.libs.json.{Reads, Json}

/**
 * Created by chicks on 6/27/15.
 *
 * {
   "results":[
      {
         "user":{
            "gender":"male",
            "name":{
               "title":"mr",
               "first":"miguel",
               "last":"jackson"
            },
            "location":{
               "street":"1898 rochestown road",
               "county":"limerick",
               "state":"connecticut",
               "zip":"62661"
            },
            "email":"miguel.jackson11@example.com",
            "username":"organicostrich462",
            "password":"broncos1",
            "salt":"47olulm6",
            "md5":"bab6cf6d9eae8f7ba3bd8b4ec188c1ac",
            "sha1":"a91e3f6422eeb8107a2ab62879c942602882f106",
            "sha256":"3c4f19aa4de79b732468c034b0f5b061a0d1e25170d20d344952821aa884e1a3",
            "registered":"1017248912",
            "dob":"884016076",
            "phone":"011-603-1066",
            "cell":"081-610-2568",
            "PPS":"5021846T",
            "picture":{
               "large":"http://api.randomuser.me/portraits/men/23.jpg",
               "medium":"http://api.randomuser.me/portraits/med/men/23.jpg",
               "thumbnail":"http://api.randomuser.me/portraits/thumb/men/23.jpg"
            },
            "version":"0.6",
            "nationality":"IE"
         },
         "seed":"461355015e18796b05"
      }
   ]
}
 */
case class PersonaName(title: String, first: String, last: String)
case class PersonaLocation(street: String, county: Option[String], city: Option[String], state: Option[String], zip: Option[String])
case class PersonaPicture(large: String, medium: String, thumbnail: String)
case class Persona(gender: String,
                   name: PersonaName,
                   location: PersonaLocation,
                   email: String,
                   username: String,
                   password: String,
                   phone: String,
                   cell: String,
                   picture: PersonaPicture,
                   nationality: String)

case class PersonaWrapper(user: Persona)
case class PersonaResult(results: Iterable[PersonaWrapper])

object PersonaJson {
  implicit def readsPersonaName: Reads[PersonaName] = Json.reads[PersonaName]

  implicit def readsPersonaLocation: Reads[PersonaLocation] = Json.reads[PersonaLocation]

  implicit def readsPersonaPicture: Reads[PersonaPicture] = Json.reads[PersonaPicture]

  implicit def readsPersona: Reads[Persona] = Json.reads[Persona]

//  implicit def readsPersonaWrapper: Reads[PersonaWrapper] = Json.reads[PersonaWrapper]
//
//  implicit def readsPersonaResult: Reads[PersonaResult] = Json.reads[PersonaResult]
}
