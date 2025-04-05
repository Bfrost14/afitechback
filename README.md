# 🎓 Application de Gestion Étudiants — Spring Boot & Angular

Cette application permet la gestion des étudiants et des notes avec un back-end en **Spring Boot** et un front-end en **Angular**.

## 📁 Structure du projet


---

## 🚀 Fonctionnalités principales

### 🖥️ Côté Angular (frontend)
- Authentification par token JWT
- Redirection automatique selon le rôle (`ROLE_SECRETAIRE`, `ROLE_ETUDIANT`)
- Pagination et tri sur les listes
- Gestion des étudiants
- Saisie et visualisation des notes
- Affichage conditionnel des boutons et composants selon le rôle

### ☕ Côté Spring Boot (backend)
- API REST sécurisée avec Spring Security + JWT
- DTOs et mapping via MapStruct
- Accès base de données via Spring Data JPA
- Gestion des utilisateurs, rôles, étudiants, notes, etc.

---

## 🔧 Lancer le projet

### ⚙️ Backend (Spring Boot)
```bash
cd myafiback
./mvnw spring-boot:run

utilisateur créer au demarrage avec le role ROLE_SECRETAIRE;
email: secretaire@myafi.com
password: admin


cd myafifront
npm install
ng serve

