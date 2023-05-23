# Zad1
### Wirtualna kamera
##### Opis:
Należy stworzyć wirtualną kamerę, która ma 6 stopni swobody ruchu. Należy uwzględnić translacje w 3 kierunkach i obrót wokół 3 osi. <br>
Głównym problemem jest wybranie odpowiedniego układu odniesienia. W układzie świata obiekty są stacjonarne, a przemieszcza się kamera. Takie ustawienie ułatwia umieszczanie obiektów, ale powoduje to utrudnione rzutowanie. W układzie kamery, kamera jest stacjonarna ale obiekty się przemieszczają.
##### Wymagania:
- translacja w 3 kierunkach
- obrót wokół 3 osi
- operacja zoom
- sterowanie ruchem z poziomu klawiatury
- możliwość powrotu do domyślego ustawienia
- program powinien pojawiać się w nowym oknie
- powinna być opcja wybrania viewport
- ruchy powinny odbywać się w kwantach
  - nie powinny być ani za małe ani za duże
  - (można by dodać opcję zmniejszenia kwantów przytrzymując CTRL i zwiększenia przytrzymując SHIFT)
- na scenie powinny znajdować się obiekty
  - zaakceptowany został pomysł z dwiema piramidami, jedna do góry nogami i zwrócone do siebie spodami, pomiędzy 4 sześciany
- obiekty mają składać się wyłącznie z linii, jeszcze nie bierzemy pod uwagę przysłaniania elementów
- operacje dotyczące linii powinny ograniczać się do MoveTo i LineTo
- linie powinny być ciągłe nieprzerywane

# Zad2
### Eliminacja elementów zasłoniętych
##### Opis:
- dla określonej grupy obiektów dokonać rzutowanie z zasłonięciem niewidocznych obiektów
- tylko wielokąty wypukłe, można założyć że to będą trójkąty
- trójkąty się nie przebijają, zachowują się przyzwoicie
- w przypadku gdy mają punkty wspólne rozsunąć je minimalnie tak aby nie były wspólne ale wciąż wizualnie się wydawały być razem
- problem kreowania obiektów na scenie
- wczytanie współrzędnych z pliku tekstowego
- możemy użyć dowolnego algorytmu, żaden ray tracing, ray casting, zebufor
- algorytm malarski lub BSPTree
- BSPTree jest trochę żmudny w pisaniu ale bardzo skuteczny
- metoda na skróty to wykorzystanie środka ciężkości ale daje to nie zawsze dobry efekt

# Zad 3
### Pokazanie jakości materiałowych wykorzystując oświetlenie
##### Opis:
- na przykład przedstwienie chropowatości kulki z pomocą światła