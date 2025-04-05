/* eslint-disable */
import { FuseNavigationItem } from '@fuse/components/navigation';

export const defaultNavigation: FuseNavigationItem[] = [
   
    {
        id: 'etudiant.note',
        title: 'Mes notes',
        type: 'basic',
        icon: 'heroicons_outline:academic-cap',
        link: '/dashboards/etudiants/mesnotes',
    },

    {
        id: 'secretaire.etudiants',
        title: 'Gestion des étudiants',
        type: 'basic',
        icon: 'heroicons_outline:academic-cap',
        link: '/dashboards/etudiants/liste',
    },

    {
        id: 'secretaire.notes',
        title: 'Gestion des notes',
        type: 'basic',
        icon: 'heroicons_outline:academic-cap',
        link: '/dashboards/notes/liste',
    },
];

