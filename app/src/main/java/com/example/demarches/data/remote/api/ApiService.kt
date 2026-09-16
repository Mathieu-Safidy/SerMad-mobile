package com.example.demarches.data.remote.api

import com.example.demarches.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @GET("api/auth/me")
    suspend fun getCurrentUser(): Response<AuthResponse>

    @GET("api/demandes")
    suspend fun getDemandes(): Response<List<DemandeResponse>>

    @GET("api/demandes/{id}")
    suspend fun getDemandeById(@Path("id") id: Long): Response<DemandeResponse>

    @POST("api/demandes")
    suspend fun creerDemande(@Body request: DemandeRequest): Response<QRCodeResponse>

    @PUT("api/demandes/{id}/valider")
    suspend fun validerDemande(@Path("id") id: Long): Response<DemandeResponse>

    @GET("api/demandes/{id}/historique")
    suspend fun getHistorique(@Path("id") id: Long): Response<List<Any>>

    @POST("api/demandes/qrcode/valider")
    suspend fun validerQRCode(@Body request: Map<String, String>): Response<ValiderQRResponse>

    @GET("api/notifications")
    suspend fun getNotifications(): Response<List<NotificationResponse>>

    @PUT("api/notifications/{id}/lire")
    suspend fun marquerNotificationLue(@Path("id") id: Long): Response<NotificationResponse>

    @GET("api/administrations")
    suspend fun getAdministrations(): Response<List<AdministrationResponse>>

    @GET("api/administrations/{id}")
    suspend fun getAdministrationById(@Path("id") id: Long): Response<AdministrationResponse>

    @GET("api/administrations/{id}/localisation")
    suspend fun getLocalisation(@Path("id") id: Long): Response<List<LocalisationResponse>>

    @GET("api/documents")
    suspend fun getDocuments(): Response<List<DocumentResponse>>

    @GET("api/documents/lieu-unique")
    suspend fun getDocumentsLieuUnique(): Response<List<DocumentResponse>>

    @GET("api/documents/{id}/lieu")
    suspend fun getDocumentLieu(@Path("id") id: Long): Response<LieuResponse>

    @GET("api/documents/procedures")
    suspend fun getProcedures(): Response<List<Any>>
}
