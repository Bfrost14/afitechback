export class UserAll {
    id: number;
    nom: string;
    prenom: string;
    password: string;
    matricule: string;
    email: string;
    filiere: string;
    role: string; // Enum or String based on your needs
    dateDeNaissance: string; // You can convert to Date if needed
    telephone: string;
    firstConnection: boolean;

    constructor(
        id: number = 0,
        nom: string = '',
        prenom: string = '',
        password: string = '',
        matricule: string = '',
        email: string = '',
        filiere: string = '',
        role: string = '', // Or an Enum if you need specific roles
        dateDeNaissance: string = '',
        telephone: string = '',
        firstConnection: boolean = false
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.matricule = matricule;
        this.email = email;
        this.filiere = filiere;
        this.role = role;
        this.dateDeNaissance = dateDeNaissance;
        this.telephone = telephone;
        this.firstConnection = firstConnection;
    }
}
