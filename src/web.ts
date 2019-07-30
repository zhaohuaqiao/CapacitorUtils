import { WebPlugin } from '@capacitor/core';
import { CapacitorUtilsPlugin } from './definitions';

export class CapacitorUtilsWeb extends WebPlugin implements CapacitorUtilsPlugin {
  constructor() {
    super({
      name: 'CapacitorUtils',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const CapacitorUtils = new CapacitorUtilsWeb();

export { CapacitorUtils };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorUtils);
