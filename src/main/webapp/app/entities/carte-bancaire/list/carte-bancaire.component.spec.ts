import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarteBancaireService } from '../service/carte-bancaire.service';

import { CarteBancaireComponent } from './carte-bancaire.component';

describe('CarteBancaire Management Component', () => {
  let comp: CarteBancaireComponent;
  let fixture: ComponentFixture<CarteBancaireComponent>;
  let service: CarteBancaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'carte-bancaire', component: CarteBancaireComponent }]), HttpClientTestingModule],
      declarations: [CarteBancaireComponent],
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
      .overrideTemplate(CarteBancaireComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarteBancaireComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CarteBancaireService);

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
    expect(comp.carteBancaires?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to carteBancaireService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getCarteBancaireIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCarteBancaireIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
