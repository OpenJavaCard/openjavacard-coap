## OpenJavaCard CoAP

This project is an experimental adaptation of the CoAP protocol to ISO7816 using JavaCard.

The intention is to create a platform for HTTP-like device interaction based on NFC and ISO7816.

Specifically, we want to build HTML-based user interfaces for smart cards with NFC and other interfaces.

### Project

For more information about our overall project, see our [website](https://openjavacard.org/).

### Status

 * Totally experimental

### Standards

We are working under consideration of the following specifications:

* ISO 7816-4 Organization, security and commands for interchange
  * We will use proprietary commands for CoAP
* ISO 7816-5 Registration of application providers
  * Currently using OpenJavaCard AID space
  * Ideally there would be a standard at some point
* RFC7252 - The Constrained Application Protocol (CoAP)
  * This is what we are implementing. 
* RFC7959 - Block-Wise Transfers in the Constrained Application Protocol (CoAP)
  * Maybe at some point.
* RFC8323 - CoAP (Constrained Application Protocol) over TCP, TLS, and WebSockets
  * ISO7816 is synchronous, so we might reuse choices for TCP

### Legal

Copyright 2019 Ingo Albrecht

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
