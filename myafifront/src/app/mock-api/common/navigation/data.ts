/* eslint-disable */
import { FuseNavigationItem } from '@fuse/components/navigation';

export const defaultNavigation: FuseNavigationItem[] = [
    {
        id      : 'administration',
        title   : 'Gestion des professeurs',
        subtitle: 'Gestion des pointages',
        type    : 'group',
        icon    : 'heroicons_outline:home',
        children: [
            {
                id   : 'professeur.pointage',
                title: 'Pointage',
                type : 'basic',
                icon : 'heroicons_outline:clipboard-check',
                link : '/professeur/pointage'
            }
        ]
    },
    {
        id      : 'gestion.scolaire',
        title   : 'Gestion scolaire',
        subtitle: 'La gestion des notes, des absences, etc...',
        type    : 'group',
        icon    : 'heroicons_outline:home',
        children: [
            {
                id   : 'gestion.absence',
                title: 'Présence',
                type : 'basic',
                icon : 'heroicons_outline:user-group',
                link : '/gestion/absence'
            },
            {
                id   : 'gestion.absence',
                title: 'Présence',
                type : 'basic',
                icon : 'heroicons_outline:user-group',
                link : '/gestion/absence'
            },
            {
                id   : 'gestion.calendrier',
                title: 'Calendrier de cours',
                type : 'basic',
                icon : 'heroicons_outline:chat-alt',
                link : '/gestion/calendrier'
            },
            {
                id   : 'gestion.cahier-texte',
                title: 'Cahier de texte',
                type : 'basic',
                icon : 'heroicons_outline:user-group',
                link : '/gestion/cahier-texte'
            },
            {
                id      : 'gestion.notation',
                title   : 'Notation de cours',
                type    : 'basic',
                icon    : 'heroicons_outline:view-boards',
                link : '/gestion/notation'
            },
            {
                id   : 'gestion.note',
                title: 'Notes',
                type : 'basic',
                icon : 'heroicons_outline:pencil-alt',
                link : '/gestion/note'
            },
        ]
    }, 
    {
        id      : 'admin.parametrage',
        title   : 'Paramétrage',
        subtitle: 'Paramétrage de l\'ensemble des données statique',
        type    : 'group',
        icon    : 'heroicons_outline:document',
        children: [
           
            {
                id      : 'admin.etudiant',
                title   : 'Etudiant',
                type    : 'basic',
                icon    : 'heroicons_outline:academic-cap',
                link : '/admin/etudiant'
            },
            
            {
                id      : 'admin.administration',
                title   : 'Administration',
                type    : 'basic',
                icon    : 'heroicons_outline:user-circle',
                link : '/admin/administration'
            },
            {
                id      : 'admin.cours',
                title   : 'Cours',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/cours/liste',
            
            },
            {
                id      : 'admin.professeur',
                title   : 'Professeur',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link : '/admin/professeur/liste'
            },
            {
                id      : 'admin.filiere',
                title   : 'Filière',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/filiere/liste',
            },
           
            {
                id      : 'admin.matiere',
                title   : 'Matière',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/matiere/liste',
            },
            
            {
                id      : 'admin.salle',
                title   : 'Salle',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/salle/liste',
            },
             {
                id   : 'admin.campus',
                title: 'Campus',
                type : 'basic',
                icon : 'heroicons_outline:cog',
                link : '/admin/campus/liste'
            },
             {
                id   : 'admin.semestre',
                title: 'Semestre',
                type : 'basic',
                icon : 'heroicons_outline:cog',
                link : '/admin/semestre/liste'
            },
            {
                id      : 'admin.ue',
                title   : 'UE',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/ue/liste',
            }, 
            {
                id      : 'admin.profile',
                title   : 'Profile',
                type    : 'basic',
                icon    : 'heroicons_outline:cog',
                link    : '/admin/profiles/liste',
            },
           
        ]
    }

];

