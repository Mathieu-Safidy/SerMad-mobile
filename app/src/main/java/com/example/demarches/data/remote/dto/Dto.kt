package com.example.demarches.data.remote.dto

data class LoginRequest(
    val email: String,
    val motDePasse: String
)

data class RegisterRequest(
    val nom: String,
    val prenom: String,
    val email: String,
    val telephone: String?,
    val motDePasse: String,
    val cin: String?,
    val dateNaissance: String
)

data class AuthResponse(
    val token: String?,
    val userId: Long?,
    val nom: String?,
    val prenom: String?,
    val email: String?,
    val profil: String?
)

data class DemandeRequest(
    val libelle: String?,
    val reference: String,
    val idProcedureMere: Long?,
    val idCitoyen: Long?
)

data class DemandeResponse(
    val idDemande: Long,
    val libelle: String?,
    val reference: String?,
    val dateDebut: Double?,
    val dateFin: Double?,
    val statutDemande: StatutDemandeResponse?,
    val procedureMere: ProcedureMereResponse?,
    val user: UserResponse?
)

data class StatutDemandeResponse(
    val idStatutDemande: Long,
    val libelle: String
)

data class ProcedureMereResponse(
    val idProcedureMere: Long,
    val document: DocumentResponse?,
    val procedureFille: ProcedureFilleResponse?
)

data class ProcedureFilleResponse(
    val idProcedureFille: Long,
    val delai: Double?,
    val cout: Double?
)

data class DocumentResponse(
    val idDocument: Long,
    val libelle: String?,
    val ageMinimum: Int?
)

data class UserResponse(
    val idUser: Long,
    val nom: String?,
    val prenom: String?,
    val email: String?
)

data class NotificationResponse(
    val idNotification: Long,
    val objetNotif: String?,
    val corpsNotif: String?,
    val dateNotif: Double?,
    val estLue: Boolean?
)

data class AdministrationResponse(
    val idAdministration: Long,
    val libelle: String?,
    val typeAdm: TypeAdmResponse?
)

data class TypeAdmResponse(
    val idTypeAdm: Long,
    val libelle: String?
)

data class LocalisationResponse(
    val idLocalisationAdm: Long,
    val libelle: String?,
    val adresse: String?,
    val longitude: Double?,
    val latitude: Double?,
    val codePostal: String?,
    val idAdministration: Long?
)

data class QRCodeResponse(
    val demande: DemandeResponse?,
    val qrCode: String?
)

data class ValiderQRResponse(
    val valide: Boolean
)
