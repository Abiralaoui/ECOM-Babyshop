import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LigneCommandeService } from '../service/ligne-commande.service';

import { LigneCommandeComponent } from './ligne-commande.component';

describe('LigneCommande Management Component', () => {
  let comp: LigneCommandeComponent;
  let fixture: ComponentFixture<LigneCommandeComponent>;
  let service: LigneCommandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'ligne-commande', component: LigneCommandeComponent }]), HttpClientTestingModule],
      declarations: [LigneCommandeComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(LigneCommandeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LigneCommandeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LigneCommandeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.ligneCommandes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to ligneCommandeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLigneCommandeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLigneCommandeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
