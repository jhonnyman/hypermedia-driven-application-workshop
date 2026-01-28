import 'htmx.org';

declare global {
    interface Window {
        htmx: any;
    }
}

import('htmx.org')
    .then(({ default: htmx }) => {
        if (typeof window !== 'undefined') {
            (window as any).htmx = htmx;
        }
        import('htmx.org/dist/ext/debug.js')
            .then(() => {
                console.log('HTMX debug mode enabled');
            })
            .catch((e) => {
                console.warn('Failed to load HTMX debug mode', e);
            });
    })
    .catch((e) => {
        console.error('Failed to load HTMX', e);
    });
