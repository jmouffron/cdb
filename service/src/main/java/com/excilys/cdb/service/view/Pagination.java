package com.excilys.cdb.service.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.PageException;
import com.excilys.cdb.service.ComputerService;

/**
 * Une classe pour gérer la pagination
 * 
 * @author excilys
 *
 */
public class Pagination {

  private List<ComputerDTO> elements;
  private int startIndex;
  private IndexPagination perPage;
  private int totalPages;
  private int currentPage;
  
  @Autowired 
  ComputerService service;
  
  public Pagination(ComputerService service, int startIndex, IndexPagination perPage) {
    this.service = service;
    this.elements = this.service.getAll().get();
    this.startIndex = startIndex;
    this.perPage = perPage;
    this.totalPages = elements.size() / this.perPage.getIndex();
    this.currentPage = 1;
  }

  public int getCurrentPage() {
    return this.currentPage;
  }

  public int getTotalPages() {
    return this.totalPages;
  }
  
  public void setPerPage(IndexPagination entitiesPerPage) throws PageException {
    switch (entitiesPerPage) {
      case IDX_10:
      case IDX_50:
      case IDX_100:
        this.perPage = entitiesPerPage;
        this.startIndex = 0;
        this.currentPage = 1;
        break;
      default:
        throw new PageException("Bad value for enitiesPerpage in pagination!"); 
    }
  }
  
  public void setElements(List<ComputerDTO> elements) {
    this.elements = elements;
    this.totalPages = this.elements.size() / this.perPage.getIndex();
  }
  
  /**
   * Navigates to the page
   * Changes the cursor to a new page
   * 
   * @param int page
   */
  public void navigate(int page) {
    while ( page < this.getCurrentPage() ) {
      this.previousPage();
    }
    while ( page > this.getCurrentPage() ) {
      this.nextPage();
    }
  }
  
  /**
   * Advances to the next page
   * 
   * @throws PageException
   */
  public void nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.startIndex += perPage.getIndex();
    }
  }

  /**
   * Goes back to the previousPage
   * 
   * @throws PageException
   */
  public void previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      if (this.currentPage != 1) {
        this.startIndex -= perPage.getIndex();
      }
    }
  }

  /**
   * Returns an Optional Nullable definite number of items from the list of
   * entities object starting from a start index
   * 
   * @param startIndex
   * @param numberToShow
   * @throws PageException
   */
  public Optional<List<ComputerDTO>> list() throws PageException {
    if (this.startIndex < 0 ) {
      throw new PageException("Non positive numbers inputted for startIndex.");
    }else if (this.perPage.getIndex() < 0) {
      throw new PageException("Non positive numbers inputted for PerPage.");
    }
    
    
    List<ComputerDTO> filteredElements = this.elements.stream().skip(startIndex).limit(perPage.getIndex())
        .collect(Collectors.toList());

    return Optional.ofNullable(filteredElements);
  }

  /**
   * Returns an table of int that represents the pages numbers available for
   * displaying elements
   * 
   * @return int[] pagesToBeDisplayed
   */
  public int[] getPages() {
    return IntStream.rangeClosed(1, this.totalPages).toArray();
  }

  public Object getSize() {
    return this.elements.size();
  }

  @Override
  public String toString() {
    return "Pagination [startIndex=" + startIndex + ", perPage=" + perPage + ", totalPages="
        + totalPages + ", currentPage=" + currentPage + "]";
  }
  
  
}
