/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Base
 *
 * @fileoverview
 *
 * @suppress {checkTypes}
 */

goog.provide('Base');

goog.require('Super');
goog.require('org.apache.flex.utils.Language');



/**
 * @constructor
 * @extends {Super}
 */
Base = function() {
  
    Base.myClassConst = new Number();
  
    this.number = this.getNumber();
  
    this.newText = this.get_text();
  
    this.newTextAgain = this.get_text();
  Base.base(this, 'constructor');
};
goog.inherits(Base, Super);


/**
 * @type {string}
 */
Base.myClassConst;


/**
 * @private
 * @type {number}
 */
Base.prototype.number;


/**
 * @private
 * @type {string}
 */
Base.prototype.newText;


/**
 * @private
 * @type {string}
 */
Base.prototype.newTextAgain;


/**
 * @export
 * @return {string}
 * @override
 */
Base.prototype.get_text = function() {
  return "A" + Base.base(this, 'get_text');
};


/**
 * @export
 * @param {string} value
 * @override
 */
Base.prototype.set_text = function(value) {
  if (value != Base.base(this, 'get_text')) {
    Base.base(this, 'set_text', "B" + value);
  }
};


/**
 * @export
 */
Base.prototype.getNumber = function() {
  alert(this.superClass_.doStuff.call(this));
  var /** @type {number} */ x = this.get_x();
};


/**
 * @export
 * @return {number}
 * @override
 */
Base.prototype.doStuff = function() {
  throw new Error("No way!");
};


/**
 * Metadata
 *
 * @type {Object.<string, Array.<Object>>}
 */
Base.prototype.FLEXJS_CLASS_INFO = { names: [{ name: 'Base', qName: 'Base'}] };
