/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React, { useState, useEffect } from 'react';
import { DesignCatalog } from '../design-preview/DesignCatalog';
import { PublicPrivacyPolicy } from '../design-preview/PublicPrivacyPolicy';

export default function App() {
  const [currentRoute, setCurrentRoute] = useState<'catalog' | 'privacidade'>('catalog');

  useEffect(() => {
    const checkRoute = () => {
      const path = window.location.pathname.toLowerCase();
      const hash = window.location.hash.toLowerCase();
      const search = window.location.search.toLowerCase();

      if (
        path.includes('/privacidade') ||
        path.includes('/privacy') ||
        hash === '#privacidade' ||
        hash === '#privacy' ||
        search.includes('page=privacidade')
      ) {
        setCurrentRoute('privacidade');
      } else {
        setCurrentRoute('catalog');
      }
    };

    checkRoute();
    window.addEventListener('popstate', checkRoute);
    window.addEventListener('hashchange', checkRoute);
    return () => {
      window.removeEventListener('popstate', checkRoute);
      window.removeEventListener('hashchange', checkRoute);
    };
  }, []);

  if (currentRoute === 'privacidade') {
    return (
      <PublicPrivacyPolicy
        onBack={() => {
          window.location.hash = '';
          setCurrentRoute('catalog');
        }}
      />
    );
  }

  return <DesignCatalog />;
}
