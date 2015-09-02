/** Set of classes used to represent the structure of the <i>/cases/</i> directory managed by PEMS.
 *  <p>
 *  Within <i>/cases/</i> there are two subdirectories: <i>/backup/</i> and <i>/live/</i>. The latter folder contains the most recently edited and saved versions of the evidence files, while the former contains every saved version of every evidence file since it was originally imported.
 *  <p>
 *  Names for the files managed by PEMS are formatted as such:
 *  <ul>
 *  	<li>Backup Files - Stored in <i>cases/backup/<code>CASENUM</code>/</i></li>
 *  	<ul>
 *  		<li>Format: "<code>CASENUM</code> (<code>FILENUM</code>-<code>VERSIONNUM</code>).<code>EXT</code>"</li>
 *  	</ul>
 *  	<li>Live Files - Stored in <i>cases/live/<code>CASENUM</code></i></li>
 *  	<ul>
 *  		<li>Format: "<code>CASENUM</code> (<code>FILENUM</code>).<code>EXT</code>"</li>
 *  	</ul>
 *  </ul>
 *  Where <code>CASENUM</code> is the number of the case, <code>FILENUM</code> is the index of the file within the case, <code>VERSIONNUM</code> is the version index of the file relative to its other versions, and <code>EXT</code> is the extension for the file.
 *  
 *  @author Jacob Jones
 *  @author Andrew Rottier
 *  @since 0.1
 *  @version 0.1
 */

package backend.storage;