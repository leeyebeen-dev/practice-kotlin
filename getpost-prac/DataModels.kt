package com.example.bustame_example

data class HTTP_GET_Model(
    var something : String? =null ,
    var users : ArrayList<UserModel>? =null
)

data class UserModel(
    var id : String? =  null,
    var busStopId : String? = null,
    var message : String? = null
)

data class PostModel(
    var id : String? =  null,
    var busStopId : String? = null,
    var message : String? = null
)

data class PostResult(
    var result : String? = null
)