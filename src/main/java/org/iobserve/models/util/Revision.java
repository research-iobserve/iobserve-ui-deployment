package org.iobserve.models.util;

import org.iobserve.models.System;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//FIXME
@Entity
public class Revision {
  @Id
  private Long id;
  private Long architectureId;
  private Long timestamp;
  @OneToOne
  private System system;

}
