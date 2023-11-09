import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'produit',
        data: { pageTitle: 'mainApp.produit.home.title' },
        loadChildren: () => import('./produit/produit.module').then(m => m.ProduitModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'mainApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'image',
        data: { pageTitle: 'mainApp.image.home.title' },
        loadChildren: () => import('./image/image.module').then(m => m.ImageModule),
      },
      {
        path: 'commande',
        data: { pageTitle: 'mainApp.commande.home.title' },
        loadChildren: () => import('./commande/commande.module').then(m => m.CommandeModule),
      },
      {
        path: 'ligne-commande',
        data: { pageTitle: 'mainApp.ligneCommande.home.title' },
        loadChildren: () => import('./ligne-commande/ligne-commande.module').then(m => m.LigneCommandeModule),
      },
      {
        path: 'carte-bancaire',
        data: { pageTitle: 'mainApp.carteBancaire.home.title' },
        loadChildren: () => import('./carte-bancaire/carte-bancaire.module').then(m => m.CarteBancaireModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'mainApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'avis',
        data: { pageTitle: 'mainApp.avis.home.title' },
        loadChildren: () => import('./avis/avis.module').then(m => m.AvisModule),
      },
      {
        path: 'admin',
        data: { pageTitle: 'mainApp.admin.home.title' },
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
