import { FuseNavigationItem } from '@fuse/components/navigation';

export const defaultNavigation: FuseNavigationItem[] = [
    {
        id: 'administration',
        title: 'Gestion des professeurs',
        subtitle: 'Gestion des pointages',
        type: 'group',
        icon: 'heroicons_outline:user-circle',
        authority: 'LECTURE_LISTE_POINTAGE_PROFESSEUR',
        children: [
            {
                id: 'gestionprofesseur.pointage',
                title: 'Pointage',
                type: 'basic',
                icon: 'heroicons_outline:clipboard-check',
                link: '/gestionprofesseur/pointage/liste',
                authority: 'LECTURE_LISTE_POINTAGE_PROFESSEUR',
            },
        ]
    },
    {
        id: 'gestion.scolaire',
        title: 'Gestion scolaire',
        subtitle: 'La gestion des notes, des cours, etc...',
        type: 'group',
        icon: 'heroicons_outline:academic-cap',
        authority: 'LECTURE_LISTE_CALENDRIER_COURS',
        children: [
            {
                id: 'gestion.espace',
                title: 'Espace étudiant',
                type: 'basic',
                icon: 'heroicons_outline:user-group',
                link: '/gestion/espace',
                authority: 'LECTURE_DETAILLE_USER'
            },
            {
                id: 'gestion.calendrier',
                title: 'Calendrier de cours',
                type: 'basic',
                icon: 'heroicons_outline:calendar',
                link: '/gestion/calendrier/liste',
                authority: 'LECTURE_LISTE_CALENDRIER_COURS',

            },
            {
                id: 'gestion.cours',
                title: 'Cours',
                type: 'basic',
                icon: 'heroicons_outline:book-open',
                link: '/gestion/cours/liste',
                authority: 'LECTURE_LISTE_COURS',

            },
            {
                id: 'gestion.note',
                title: 'Notes',
                type: 'basic',
                icon: 'heroicons_outline:pencil-alt',
                link: '/gestion/note/liste',
                authority: 'LECTURE_LISTE_NOTE',
            },

        ]
    },
    {
        id: 'admin.parametrage',
        title: 'Paramétrage',
        subtitle: 'Paramétrage des données de base',
        type: 'group',
        icon: 'heroicons_outline:cog',
        authority: 'ENREGISTREMENT_USER',
        children: [
            {
                id: 'admin.etudiant',
                title: 'Étudiants',
                type: 'basic',
                icon: 'heroicons_outline:user-group',
                link: '/admin/etudiant/liste',
                authority: 'ENREGISTREMENT_USER',

            },
            {
                id: 'admin.professeur',
                title: 'Professeurs',
                type: 'basic',
                icon: 'heroicons_outline:academic-cap',
                link: '/admin/professeur/liste',
                authority: 'MODIFICATION_USER',
                
            },
            {
                id: 'admin.administration',
                title: 'Administrateurs',
                type: 'basic',
                icon: 'heroicons_outline:shield-check',
                link: '/admin/administration/liste',
                authority: 'ENREGISTREMENT_USER',
                
            },
            {
                id: 'admin.affectation',
                title: 'Affectations matières',
                type: 'basic',
                icon: 'heroicons_outline:adjustments',
                link: '/admin/affectation/liste',
                authority: 'ENREGISTREMENT_MATIERE_USER',
                
            },
            {
                id: 'admin.filiere',
                title: 'Filières',
                type: 'basic',
                icon: 'heroicons_outline:template',
                link: '/admin/filiere/liste',
                authority: 'ENREGISTREMENT_FILIERE',
                
            },
            {
                id: 'admin.matiere',
                title: 'Matières',
                type: 'basic',
                icon: 'heroicons_outline:book-open',
                link: '/admin/matiere/liste',
                authority:'ENREGISTREMENT_MATIERE',
                
            },
            {
                id: 'admin.ue',
                title: 'Unités d\'enseignement',
                type: 'basic',
                icon: 'heroicons_outline:collection',
                link: '/admin/ue/liste',
                authority: 'ENREGISTREMENT_UE',

                
            },
            {
                id: 'admin.salle',
                title: 'Salles',
                type: 'basic',
                icon: 'heroicons_outline:office-building',
                link: '/admin/salle/liste',
                authority: 'ENREGISTREMENT_SALLE'

                
            },
            {
                id: 'admin.campus',
                title: 'Campus',
                type: 'basic',
                icon: 'heroicons_outline:location-marker',
                link: '/admin/campus/liste',
                authority: 'ENREGISTREMENT_CAMPUSES',

                
            },
            {
                id: 'admin.semestre',
                title: 'Semestres',
                type: 'basic',
                icon: 'heroicons_outline:clock',
                link: '/admin/semestre/liste',
                authority: 'ENREGISTREMENT_SEMESTRE',

                
            },
            {
                id: 'admin.annee',
                title: 'Années scolaires',
                type: 'basic',
                icon: 'heroicons_outline:calendar',
                link: '/admin/annee/liste',
                authority:  'ENREGISTREMENT_ANNEESCOLAIRE',

                
            },
            {
                id: 'admin.profile',
                title: 'Profils',
                type: 'basic',
                icon: 'heroicons_outline:user-circle',
                link: '/admin/profiles/liste',
                authority: 'ENREGISTREMENT_PROFIL',
                
            },
        ]
    }
];